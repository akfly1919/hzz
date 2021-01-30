package com.akfly.hzz.service;


import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 客户账单流水对应表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-24
 */
public interface CustomerbillrelatedService extends IService<CustomerbillrelatedVo> {
    public List<CustomerbillrelatedVo> getCustomerbillrelatedById(Long id, int pageSize, int pageNum);
}
