package com.akfly.hzz.service;

import com.akfly.hzz.vo.TaskinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任务信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TaskinfoService extends IService<TaskinfoVo> {

    List<TaskinfoVo> getTaskinfoVos(long userId, int pageNum, int pageSize);

}
