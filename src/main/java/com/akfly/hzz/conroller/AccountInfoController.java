package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.PayChannelEnum;
import com.akfly.hzz.constant.PayStatus;
import com.akfly.hzz.constant.ValidEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomercashoutinfoService;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.akfly.hzz.service.TradeorderinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import com.akfly.hzz.vo.TradeorderinfoVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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


    @ApiOperation(value="用户充值",notes="用户登录就可以")
    @PostMapping(value = "/recharge")
    @VerifyToken
    public BaseRspDto<List<CustomerpayinfoVo>> recharge(int pageNum, int pageSize, Date beginTime, Date endTime){

        BaseRspDto<List<CustomerpayinfoVo>> rsp = new BaseRspDto<List<CustomerpayinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            CustomerpayinfoVo vo = new CustomerpayinfoVo();
            //vo.setCaiAmount(copy.getCaiAmount());
            vo.setCbiId(String.valueOf(userInfo.getCbiId()));
            //vo.setCpiPaytime(new Date());
            //vo.setCpiFinishtime(copy.getCpiFinishtime());
            vo.setCpiPaystatus(PayStatus.UN_PAY.getStatus());
            vo.setCpiValid(ValidEnum.VALID.getStatus());
            vo.setCpiChannel(PayChannelEnum.);
            //vo.setCpiChannelorderid(copy.getCpiChannelorderid());
            //vo.setCpiOperator(copy.getCpiOperator());

            customerpayinfoService.insertCustomerPayInfo(vo);
        //    rsp.setData(list);
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
    @PostMapping(value = "/withdraw")
    @VerifyToken
    public BaseRspDto<List<TradeorderinfoVo>> withdraw(int pageNum, int pageSize, Date beginTime, Date endTime){
        BaseRspDto<List<TradeorderinfoVo>> rsp = new BaseRspDto<List<TradeorderinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
        //    List<TradeorderinfoVo> list = tradeorderinfoService.getTradeorderinfoVo(pageNum, pageSize, userInfo.getCbiId(), beginTime, endTime);
        //    rsp.setData(list);
        //} catch (HzzBizException e) {
        //    log.error("用户提现业务错误 msg={}", e.getErrorMsg(), e);
        //    rsp.setCode(e.getErrorCode());
        //    rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("用户提现系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

}
