package com.akfly.hzz.channel;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.constant.CreditCardLimitEnum;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("aliPayH5SubmitPayService")
public class AliPayH5SubmitPayServiceImpl extends SubmitPayService<SubmitPayModel> {

    // 请求地址
    @Value("${alipay.wap.unifiedorder}")
    private String REQUEST_URL;

    // 异步通知地址
    @Value("${alipay.wap.notify_url}")
    private String NOTIFY_URL;

    // 支付结束回显url
    @Value("${alipay.wap.url_return}")
    private String URL_RETURN;

    @Value("${alipay.wap.privateKey}")
    private String privateKey;

    @Value("${alipay.wap.publicKey}")
    private String publicKey;

    @Value("${alipay.wap.appid}")
    private String appId;

    private AlipayClient client;


    @Override
    protected Map generateReqParam(SubmitPayModel model) throws HzzBizException {
        // 商户订单失效时间已过
        if (model.getMchOrderExpDate().compareTo(new Date()) < 0) {
            throw new HzzBizException(HzzExceptionEnum.ORDER_ALREADY_EXPIRED);
        }

        JSONObject jsonObject = new JSONObject();
        // 商品订单标题
        jsonObject.put("subject", model.getMchOrderName());
        jsonObject.put("out_trade_no", model.getTransId());
        // 支付宝以元为单位保留2位小数，需要将分转换为元。取值范围[0.01,100000000]
        // jsonObject.put("total_amount", String.valueOf(model.getPayAmount() / 100F));
        jsonObject.put("total_amount",
                       BigDecimal.valueOf(model.getPayAmount()).divide(BigDecimal.valueOf(100)).toString());
        // 订单超时时间查，以秒为单位
        Long timeoutExpress = (model.getMchOrderExpDate().getTime() - model.getMchOrderStartTime().getTime()) / 1000;

        // 支付宝超时时间，以分为单位取值范围：1m～15d，传0m支付时提示参数错误
        timeoutExpress = timeoutExpress / 60;
        jsonObject.put("timeout_express", timeoutExpress + "m");
        jsonObject.put("time_expire",DateUtil.getStringFromDate(model.getMchOrderExpDate(), DateUtil.FORMAT_DATETIME_HM));

        // 销售产品码，写固定值QUICK_WAP_PAY
        jsonObject.put("product_code", "QUICK_WAP_PAY");

        if (CreditCardLimitEnum.NO_CREDIT == model.getLimit_pay()) {
            jsonObject.put("disable_pay_channels", "credit_group");
        }

        // 将请求参数转换为JSON格式
        String bizContent = jsonObject.toJSONString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("bizContent", bizContent);
        params.put("notify_url", NOTIFY_URL);
        params.put("urlReturn", URL_RETURN);

        log.info("AliPayH5SubmitPayServiceImpl.generateReqParam()|请求支付宝参数 ={} ", bizContent);
        client = new DefaultAlipayClient(REQUEST_URL, appId, privateKey, "json",
                                         CommonConstant.CHARSET_UTF8, publicKey, model.getSignType());
        return params;
    }

    @Override
    protected Map channelPay(Map param) throws HzzBizException {
        String form = "";
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        // 设置业务请求参数的集合，JSON格式
        String bizContent = (String) param.remove("bizContent");
        request.setBizContent(bizContent);

        // 设置后台通知地址
        String notifyUrl = (String) param.remove("notify_url");
        request.setNotifyUrl(notifyUrl);

        // 设置前台通知地址
        String urlReturn = (String) param.remove("urlReturn");
        request.setReturnUrl(urlReturn);

        try {
            // 页面跳转
            form = client.pageExecute(request).getBody();
            param.put("form", form);
        } catch (AlipayApiException e) {
            log.error("生成支付宝请求页面异常，请求参数={}", bizContent, e);
            throw new HzzBizException(HzzExceptionEnum.REQUEST_ALIPAY_ERROR);
        }
        return param;
    }

    @Override
    protected SubmitPayResultModel getResult(SubmitPayModel model, Map param) throws HzzBizException {
        SubmitPayResultModel submitPayResultModel = new SubmitPayResultModel();
        ////没有指定输出类型默认使用Image即页面跳转形式
        //if (model.getOutputType() == null || OutputTypeEnum.IMAGE.equals(model.getOutputType())) {
        //    submitPayResultModel.setOutputType(OutputTypeEnum.IMAGE);
        //    submitPayResultModel.setResource(ResourceEnum.ALIPAY_PAY.getUrl());
        //} else if(OutputTypeEnum.REPORT.equals(model.getOutputType()) ||
        //        OutputTypeEnum.DEEPLINK.equals(model.getOutputType()) ||
        //        OutputTypeEnum.ALI_NATIVE_IPAY.equals(model.getOutputType())){//使用传入输出格式
        //    submitPayResultModel.setOutputType(model.getOutputType());
        //    // 如果返回报文需要增加一下参数
        //    param.put("appId", model.getAppId());
        //    param.put("nowPayOrderNo", model.getTransId());
        //    param.put("funcode", CommonConfig.FUNCODE_ORDER);
        //    param.put("mhtOrderNo", model.getMchOrderId());
        //    param.put("responseTime", DateUtil.getStringFromDate(new Date(), DateUtil.FORMAT_TRADETIME));
        //}else if(OutputTypeEnum.WX_ALIPAY.equals(model.getOutputType())){//微信内调用支付宝
        //    submitPayResultModel.setOutputType(OutputTypeEnum.IMAGE);
        //    submitPayResultModel.setResource(ResourceEnum.ALIPAY_PAY_WX.getUrl());
        //}else {
        //    throw new HzzBizException(HzzBizExceptionEnum.OUTPUTTYPE_ERROR);
        //}
        //submitPayResultModel.setRetContent(param);
        //return submitPayResultModel;
        //}
        try {
            String result = String.valueOf(param.get("form"));
            log.info("支付宝H5下单返回参数 result={}", result);
            submitPayResultModel.setResult(URLEncoder.encode(result, CommonConstant.CHARSET_UTF8));
        } catch (UnsupportedEncodingException e) {
            log.error("支付宝返回页面form异常-encode", e);
        }
        return submitPayResultModel;
    }
}
