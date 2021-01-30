package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.channel.AliPayAppSubmitPayServiceImpl;
import com.akfly.hzz.channel.SubmitPayModel;
import com.akfly.hzz.channel.SubmitPayResultModel;
import com.akfly.hzz.channel.SubmitPayService;
import com.akfly.hzz.constant.CreditCardLimitEnum;
import com.akfly.hzz.constant.PayStatus;
import com.akfly.hzz.constant.ValidEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.service.CustomercashoutinfoService;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.akfly.hzz.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/account")
public class AccountInfoController {

    @Resource
    private CustomerpayinfoService customerpayinfoService;

    @Resource
    private CustomercashoutinfoService customercashoutinfoService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

    @Resource(name = "aliPayH5SubmitPayService")
    private SubmitPayService aliPayH5SubmitPayService;


    @ApiOperation(value="用户充值",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="amount",value="充值金额(整数不带小数)",required=true),
            @ApiImplicitParam(name="payChannel",value="支付渠道(1：微信 2：支付宝)",required=true)
    })
    @PostMapping(value = "/recharge")
    @VerifyToken
    public BaseRspDto<SubmitPayResultModel> recharge(@RequestParam @Digits(integer = 10, fraction = 0) Integer amount,
                                                        @RequestParam Integer payChannel){

        BaseRspDto<SubmitPayResultModel> rsp = new BaseRspDto<SubmitPayResultModel>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            if (payChannel != 2) {
                throw new HzzBizException(HzzExceptionEnum.NOT_SUPPORT_PAY);
            }
            CustomerpayinfoVo vo = new CustomerpayinfoVo();
            vo.setCaiAmount(amount);
            vo.setCbiId(String.valueOf(userInfo.getCbiId()));

            Date date = new Date();
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            vo.setCpiPaytime(instant.atZone(zoneId).toLocalDateTime());
            //vo.setCpiFinishtime(copy.getCpiFinishtime());
            vo.setCpiPaystatus(PayStatus.UN_PAY.getStatus());
            vo.setCpiValid(ValidEnum.VALID.getStatus());
            vo.setCpiChannel(payChannel);
            //vo.setCpiChannelorderid(copy.getCpiChannelorderid());
            //vo.setCpiOperator(copy.getCpiOperator());

            long orderId = customerpayinfoService.insertCustomerPayInfo(vo);
            log.info("orderId={}", orderId);
            //customerpayinfoService.lambdaQuery().
            SubmitPayModel submitPayModel = new SubmitPayModel();
            submitPayModel.setTransId(String.valueOf(orderId));
            submitPayModel.setPayAmount(Long.valueOf(amount));
            submitPayModel.setMchOrderName("订单");
            submitPayModel.setMchOrderDetail("订单");
            submitPayModel.setLimit_pay(CreditCardLimitEnum.NO_CREDIT);
            Date startTime = new Date();
            submitPayModel.setMchOrderStartTime(startTime);
            submitPayModel.setMchOrderExpDate(DateUtils.addHours(startTime, 2));
            submitPayModel.setSignType("RSA2");
            //submitPayModel.setMhtReserved(copy.getMhtReserved());
            //submitPayModel.setCliDeviceType(copy.getCliDeviceType());

            SubmitPayResultModel model = aliPayH5SubmitPayService.submitPay(submitPayModel);
            log.info("支付宝H5下单返回参数 result={}", model.getResult());
            rsp.setData(model);
        } catch (HzzBizException e) {
            log.error("用户充值业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("用户充值系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="用户提现",notes="用户登录就可以")
    @RequestMapping(value = "/withdraw", method = {RequestMethod.PUT, RequestMethod.POST})
    @VerifyToken
    public BaseRspDto<String> withdraw(@Validated CustomercashoutinfoVo customercashoutinfoVo){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        Assert.notNull(customercashoutinfoVo.getCcoiAccount(), "提现账户信息不完整");
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
             customercashoutinfoVo.setCbiId(userInfo.getCbiId());
             customercashoutinfoVo.setCcoiUpdatetime(LocalDateTime.now());
             customercashoutinfoService.createcustomercashoutinfo(customercashoutinfoVo);
        } catch (HzzBizException e) {
            log.error("用户提现业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("用户提现系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @ApiOperation(value="用户流水",notes="用户登录就可以")
    @PostMapping(value = "/customerBill")
    @VerifyToken
    public BaseRspDto<List<CustomerbillrelatedVo>> getCustomerBill(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg, @RequestParam @Digits(integer = 10,fraction = 0) Integer size){
        BaseRspDto<List<CustomerbillrelatedVo>> rsp = new BaseRspDto<List<CustomerbillrelatedVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<CustomerbillrelatedVo> list = customerbillrelatedService.getCustomerbillrelatedById(userInfo.getCbiId(), size, beg);
            rsp.setData(list);
        }  catch (Exception e) {
            log.error("用户提现系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


}
