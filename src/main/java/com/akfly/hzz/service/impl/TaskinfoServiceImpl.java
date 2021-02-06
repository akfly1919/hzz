package com.akfly.hzz.service.impl;

import com.akfly.hzz.vo.TaskinfoVo;
import com.akfly.hzz.mapper.TaskinfoMapper;
import com.akfly.hzz.service.TaskinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TaskinfoServiceImpl extends ServiceImpl<TaskinfoMapper, TaskinfoVo> implements TaskinfoService {


    @Override
    public List<TaskinfoVo> getTaskinfoVos(long userId, int pageNum, int pageSize) {

        List<TaskinfoVo> taskinfoVos = lambdaQuery().eq(TaskinfoVo::getCbiId, userId)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        return taskinfoVos;
    }
}
