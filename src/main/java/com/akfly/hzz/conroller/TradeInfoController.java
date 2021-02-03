package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomeraddressinfoService;
import com.akfly.hzz.service.TradeorderinfoService;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.TradeorderinfoVo;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/trade")
@Validated
public class TradeInfoController {

    @Resource
    private TradeorderinfoService tradeorderinfoService;

    @ApiOperation(value="获取购买订单",notes="用户登录就可以")
    @PostMapping(value = "/getTradeOrder")
    @VerifyToken
    public BaseRspDto<List<TradeorderinfoVo>> getTradeOrder(int pageNum, int pageSize, Date beginTime, Date endTime){
        BaseRspDto<List<TradeorderinfoVo>> rsp = new BaseRspDto<List<TradeorderinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<TradeorderinfoVo> list = tradeorderinfoService.getTradeorderinfoVo(pageNum, pageSize, userInfo.getCbiId(), beginTime, endTime);
            rsp.setData(list);
        } catch (HzzBizException e) {
            log.error("获取购买订单业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取购买订单系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @ApiOperation(value="购买",notes="用户登录就可以")
    @PostMapping(value = "/buy")
    @VerifyToken
    public BaseRspDto<String> buy(@RequestParam @NotNull @Digits(integer = 6,fraction = 2) Double price,@RequestParam @NotNull Long gbid,@RequestParam @NotNull Integer num){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            tradeorderinfoService.nomalBuy(userInfo.getCbiId(),gbid.longValue(),num,price.doubleValue());
        } catch (HzzBizException e) {
            log.error("购买业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("购买系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

}
