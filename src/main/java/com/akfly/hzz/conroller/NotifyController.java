package com.akfly.hzz.conroller;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.constant.PayStatus;
import com.akfly.hzz.constant.ValidEnum;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.util.RSAUtil;
import com.akfly.hzz.util.RequestUtil;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/notify")
public class NotifyController {

    @Value("${alipay.wap.publicKey}")
    private String publicKey;

    @Resource
    private CustomerpayinfoService customerpayinfoService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;



    @PostMapping(value = "/aliPayNotify")
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response){

        try {
            //获取通知内容
            String reqBodyStr = RequestUtil.getRequestBodyString(request);
            Map<String, String> notifyMap = RequestUtil.splitString(reqBodyStr);
            log.info("NotifyController.aliPayNotify|通知内容："+notifyMap);

            //验签
            String aliSign = notifyMap.get("sign");
            notifyMap.remove("sign");
            notifyMap.remove("sign_type");
            String signStr = RequestUtil.postFormLinkReport(notifyMap);
            boolean flag = RSAUtil.verify(signStr.getBytes("UTF-8"), publicKey, aliSign);
            if (!flag) {
                log.error("aliPayNotify支付宝后台通知验签失败，transId={}", notifyMap.get("out_trade_no"));
                //return CommonConstant.RESPTO_BANK_FAIL_MSG; // TODO 需要放开
            }

            //验签通过，开始处理通知
            String resultCode = notifyMap.get("trade_status");
            if (CommonConstant.ALI_SUCCESS_RESP_CODE.equals(resultCode)) {
                String transId = notifyMap.get("out_trade_no");
                String channelTransId = notifyMap.get("trade_no");
                String amount = notifyMap.get("total_amount");

                CustomerpayinfoVo vo = new CustomerpayinfoVo();
                vo.setCpiOrderid(Long.valueOf(transId));
                BigDecimal b_amount = new BigDecimal(amount);
                String transAmount = String.valueOf(b_amount.multiply(new BigDecimal(100)).longValue());
                vo.setCaiAmount(Integer.parseInt(transAmount));
                String payTime = notifyMap.get("gmt_payment");
                Date date = DateUtils.parseDate(payTime, "yyyy-MM-dd HH:mm:ss");
                vo.setCpiFinishtime(DateUtil.getLocalDateTime(date));
                vo.setCpiUpdatetime(DateUtil.getLocalDateTime(new Date()));
                vo.setCpiPaystatus(PayStatus.PAYED.getStatus());
                vo.setCpiChannelorderid(channelTransId);
                customerpayinfoService.rechargeSuccess(vo, amount);
                return CommonConstant.RESPTO_BANK_SUCCESS_MSG;
            }
        } catch (HzzBizException e) {
            log.error("aliPayNotify后台通知Payment异常，"+e.getErrorMsg(),e);
        } catch (Exception e) {
            log.error("aliPayNotify后台通知异常，"+e.getMessage(),e);
        }
        return CommonConstant.RESPTO_BANK_FAIL_MSG;
    }


}
