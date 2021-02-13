package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.GoodstaskinfoService;
import com.akfly.hzz.vo.GoodstaskinfoVo;
import com.akfly.hzz.vo.TaskstatisticsVo;
import com.akfly.hzz.mapper.TaskstatisticsMapper;
import com.akfly.hzz.service.TaskstatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 任务指标统计表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-07
 */
@Service
@Slf4j
public class TaskstatisticsServiceImpl extends ServiceImpl<TaskstatisticsMapper, TaskstatisticsVo> implements TaskstatisticsService {

    @Resource
    private GoodstaskinfoService goodstaskinfoService;

    @Override
    public void saveOrUpdateForNoSpecial(long cbiid, long gbid, int num) throws HzzBizException {

        try {
            GoodstaskinfoVo goodstaskinfoVo = goodstaskinfoService.getGoodstaskinfoVo(gbid);
            TaskstatisticsVo voInDb = lambdaQuery().eq(TaskstatisticsVo::getGbiId, gbid)
                    .eq(TaskstatisticsVo::getCbiId, cbiid).one();
            if (voInDb == null) {
                TaskstatisticsVo vo = new TaskstatisticsVo();
                vo.setGbiId(gbid);
                vo.setCbiId(cbiid);
                //vo.setBuyNum(copy.getBuyNum());
                //vo.setPickupNum(copy.getPickupNum());
                vo.setUsedBuyNum(num * goodstaskinfoVo.getGtiBuynum());
                vo.setUsedPickupNum(num * goodstaskinfoVo.getGtiPickupnum());
                vo.setToiCreatetime(LocalDateTime.now());
                vo.setToiUpdatetime(LocalDateTime.now());
                saveOrUpdate(vo);
            } else {
                voInDb.setUsedBuyNum(num * goodstaskinfoVo.getGtiBuynum() + voInDb.getUsedBuyNum());
                voInDb.setUsedPickupNum(num * goodstaskinfoVo.getGtiPickupnum() + voInDb.getUsedPickupNum());
                voInDb.setToiUpdatetime(LocalDateTime.now());
                saveOrUpdate(voInDb);
            }
        } catch (Exception e) {
            log.error("更新任务表特价商品已使用数量异常 cbiid={} gbid={}", cbiid, gbid, e);
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    public TaskstatisticsVo getTaskInfo(long cbiid, long gbid) {

        TaskstatisticsVo vo = lambdaQuery().eq(TaskstatisticsVo::getCbiId, cbiid).eq(TaskstatisticsVo::getGbiId, gbid).one();
        return vo;
    }

    @Override
    public void saveOrUpdateWithStatics(TaskstatisticsVo vo) throws HzzBizException {

        if (!saveOrUpdate(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
