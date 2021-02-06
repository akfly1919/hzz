package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
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
import com.akfly.hzz.service.TaskinfoService;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/task")
public class TaskInfoController {

    @Resource
    private TaskinfoService taskinfoService;

    @ApiOperation(value="我的任务列表",notes="要求用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="第几页",required=true),
            @ApiImplicitParam(name="size",value="每页显示多少条",required=true)
    })
    @PostMapping(value = "/myTask")
    @VerifyToken
    public BaseRspDto<List<TaskinfoVo>> recharge(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                     @RequestParam @Digits(integer = 10,fraction = 0) Integer size){

        BaseRspDto<List<TaskinfoVo>> rsp = new BaseRspDto<List<TaskinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<TaskinfoVo> list = taskinfoService.getTaskinfoVos(userInfo.getCbiId(), beg, size);
            rsp.setData(list);
        //} catch (HzzBizException e) {
        //    log.error("我的任务列表业务错误 msg={}", e.getErrorMsg(), e);
        //    rsp.setCode(e.getErrorCode());
        //    rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("我的任务列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


}
