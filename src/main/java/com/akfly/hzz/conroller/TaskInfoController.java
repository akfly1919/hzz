package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.TaskGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.*;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private CustomerpickupinfoService customerpickupinfoService;

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private GoodstaskinfoService goodstaskinfoService;


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
                    TaskstatisticsVo taskVo = new TaskstatisticsVo();
                    taskVo.setCbiId(userInfo.getCbiId());
                    taskVo.setGbiId(vo.getGbiId());
                    taskVo.setBuyNum(vo.getStock());
                    taskVo.setUsedBuyNum(0);
                    taskVo.setUsedPickupNum(0);
                    taskVo.setPickupNum(num);
                    taskVo.setToiCreatetime(LocalDateTime.now());
                    taskVo.setToiUpdatetime(LocalDateTime.now());
                    taskstatisticsService.saveOrUpdate(taskVo);
                } else {
                    taskGoodsDto.setBuyNum(Math.max(vo.getStock() - taskInfo.getUsedBuyNum(), 0));
                    int pickNum = num - taskInfo.getUsedPickupNum();
                    taskGoodsDto.setPickUpNum(Math.max(pickNum, 0));
                    taskInfo.setBuyNum(vo.getStock());
                    taskInfo.setPickupNum(num);
                    taskInfo.setToiUpdatetime(LocalDateTime.now());
                    taskstatisticsService.saveOrUpdate(taskInfo);
                }
                GoodstaskinfoVo goodstaskinfoVo = goodstaskinfoService.getGoodstaskinfoVo(vo.getGbiId());
                log.info("获取商品配置任务信息返回 gbiid={} goodstaskinfoVo={}", vo.getGbiId(), JsonUtils.toJson(goodstaskinfoVo));
                int buyConfig = goodstaskinfoVo.getGtiBuynum();
                int pickUpConfig = goodstaskinfoVo.getGtiPickupnum();
                boolean flag = taskGoodsDto.getPickUpNum() >= pickUpConfig && taskGoodsDto.getBuyNum() >= buyConfig;
                int canBuy = (flag ? 1 : 0);
                taskGoodsDto.setDiscountNumConfig(goodstaskinfoVo.getGtiDiscountnum());
                taskGoodsDto.setBuyConfig(buyConfig);
                taskGoodsDto.setPickUpConfig(pickUpConfig);
                try {
                    int buy = taskGoodsDto.getBuyNum()/buyConfig;
                    int pickUp = taskGoodsDto.getPickUpNum()/pickUpConfig;
                    taskGoodsDto.setCanBuyNum(Math.min(buy, pickUp) * goodstaskinfoVo.getGtiDiscountnum());
                } catch (Exception e) {
                    log.error("计算可以购买特价商品数量异常--商品任务信息配置错误", e);
                    taskGoodsDto.setCanBuyNum(0);
                    taskGoodsDto.setCanBuy(0);
                }

                taskGoodsDto.setCanBuy(canBuy);
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
