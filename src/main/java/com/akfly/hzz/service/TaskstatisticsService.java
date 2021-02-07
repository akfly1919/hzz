package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TaskstatisticsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任务指标统计表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-07
 */
public interface TaskstatisticsService extends IService<TaskstatisticsVo> {

    void saveOrUpdateForNoSpecial(long cbiid,long gbid,int num) throws HzzBizException;

    TaskstatisticsVo getTaskInfo(long cbiid,long gbid);

}
