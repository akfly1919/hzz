package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.channel.SubmitPayModel;
import com.akfly.hzz.channel.SubmitPayResultModel;
import com.akfly.hzz.channel.SubmitPayService;
import com.akfly.hzz.constant.CreditCardLimitEnum;
import com.akfly.hzz.constant.PayStatus;
import com.akfly.hzz.constant.ValidEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.TaskGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/task")
public class TaskInfoController {

    @Resource
    private TaskinfoService taskinfoService;

    @Resource
    private TaskstatisticsService taskstatisticsService;


    @Resource
    private TradeorderinfoService tradeorderinfoService;

    @Resource
    private TradepredictinfoService tradepredictinfoService;

    @Resource
    private CustomerpickupinfoService customerpickupinfoService;

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;


    @ApiOperation(value="我的任务列表",notes="要求用户登录")
    @PostMapping(value = "/myTask")
    @VerifyToken
    public BaseRspDto<List<TaskGoodsDto>> getTasks(){

        BaseRspDto<List<TaskGoodsDto>> rsp = new BaseRspDto<List<TaskGoodsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<TradeorderinfoVo> tradeorderinfoVos = tradeorderinfoService.getBuyNoSpecialTrade(userInfo.getCbiId());
            List<TaskGoodsDto> taskGoodsDtoList = new ArrayList<>();
            for (TradeorderinfoVo vo : tradeorderinfoVos) {
                TaskGoodsDto taskGoodsDto = new TaskGoodsDto();
                GoodsbaseinfoVo goodsbaseinfoVo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                BeanUtils.copyProperties(goodsbaseinfoVo, taskGoodsDto);
                int num = customerpickupinfoService.getPickUpNum(userInfo.getCbiId(), vo.getGbiId());
                TaskstatisticsVo taskInfo = taskstatisticsService.getTaskInfo(userInfo.getCbiId(), vo.getGbiId());
                if (taskInfo == null) {
                    taskGoodsDto.setBuyNum(vo.getStock());
                    taskGoodsDto.setPickUpNum(num);
                } else {
                    taskGoodsDto.setBuyNum(Math.max(vo.getStock() - taskInfo.getUsedBuyNum(), 0));
                    int pickNum = num - taskInfo.getUsedPickupNum();
                    taskGoodsDto.setPickUpNum(Math.max(pickNum, 0));
                }
                taskGoodsDtoList.add(taskGoodsDto);
            }
            rsp.setData(taskGoodsDtoList);

        } catch (HzzBizException e) {
            log.error("我的任务列表业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("我的任务列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


}
