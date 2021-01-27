package com.akfly.hzz.channel;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.constant.CreditCardLimitEnum;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AliPayAppSubmitPayServiceImpl extends SubmitPayService<SubmitPayModel> {

    // 异步通知地址
    @Value("${alipayapp.sdk.notify_url}")
    private String NOTIFY_URL = "";

    // 支付结束回显url
    @Value("${alipayapp.sdk.method}")
    private String METHOD = "";


    @Override
    protected Map generateReqParam(SubmitPayModel model) throws HzzBizException {
        // 商户订单失效时间已过
        if (model.getMchOrderExpDate().compareTo(new Date()) < 0)
            throw new HzzBizException(HzzExceptionEnum.ORDER_ALREADY_EXPIRED);

        JSONObject jsonObject = new JSONObject();
        // 商品订单标题
        jsonObject.put("subject", model.getMchOrderName());
        jsonObject.put("out_trade_no", model.getTransId());
        // 支付宝以元为单位保留2位小数，需要将分转换为元。取值范围[0.01,100000000]
        // jsonObject.put("total_amount", String.valueOf(model.getPayAmount() / 100F));
        jsonObject.put("total_amount",
                       BigDecimal.valueOf(model.getPayAmount()).divide(BigDecimal.valueOf(100)).toString());
        // 销售产品码，写固定值QUICK_MSECURITY_PAY
        jsonObject.put("product_code", "QUICK_MSECURITY_PAY");
        // 商品详情
        if (StringUtils.isNotEmpty(model.getMchOrderDetail())) {
            jsonObject.put("body", model.getMchOrderDetail());
        }
        // 禁用支付渠道，禁用信用卡
        if (CreditCardLimitEnum.NO_CREDIT.equals(model.getLimit_pay())) {
            jsonObject.put("disable_pay_channels", "credit_group");
        }

        // 业务扩展参数,系统商编号
        JSONObject extendJsonObj = new JSONObject();
        //extendJsonObj.put("sys_service_provider_id", model.getChPid());
        jsonObject.put("extend_params", extendJsonObj.toJSONString());

        // 将请求参数转换为JSON格式
        String bizContent = jsonObject.toJSONString();

        Map<String, String> keyValues = new HashMap<String, String>();
        keyValues.put("app_id", model.getChAppId());
        keyValues.put("method", METHOD);
        keyValues.put("format", "JSON");
        keyValues.put("charset", CommonConstant.CHARSET_UTF8);
        keyValues.put("sign_type", "RSA");
        keyValues.put("timestamp", DateUtil.getStringFromDate(new Date(), DateUtil.FORMAT_DATETIME));
        keyValues.put("version", "1.0");
        keyValues.put("notify_url", NOTIFY_URL);
        //keyValues.put("app_auth_token", model.getAppAuthToken());
        keyValues.put("biz_content", bizContent);
        String orderParam = SignUtil.buildOrderParam(keyValues);

        // 使用私钥生成sign
        String sign = SignUtil.getSign(keyValues, model.getChAppKey());

        // 生成支付串
        String orderInfo = orderParam + "&" + sign;

        log.info("AliPayAppSubmitPayServiceImpl.generateReqParam()|请求支付宝参数 = " + bizContent);
        keyValues.clear();
        keyValues.put("orderInfo", orderInfo);
        //keyValues.put("respOutputType", String.valueOf(OutputTypeEnum.ALIPAY.getCode()));
        return keyValues;
    }

    /**
     * 支付宝APP不用请求支付地址
     *
     * @param param
     * @return
     * @throws HzzBizException
     */
    @Override
    protected Map channelPay(Map param) throws HzzBizException {
        return param;
    }

    @Override
    protected SubmitPayResultModel getResult(SubmitPayModel model, Map param) throws HzzBizException {
        SubmitPayResultModel submitPayResultModel = new SubmitPayResultModel();
        //submitPayResultModel.setOutputType(OutputTypeEnum.ALIPAY);
         String info = getJSONString(model,param);
        submitPayResultModel.setRetContent(param);
        return submitPayResultModel;
    }

    /**
     * 返回移动端调起支付宝app的串
     * @param model
     * @param param
     * @return
     */
    public String getJSONString(SubmitPayModel model, Map param) {

        return String.valueOf(param.get("orderInfo"));
    }
}
