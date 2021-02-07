package com.akfly.hzz.service;


import com.akfly.hzz.constant.InOrOutTypeEnum;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
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
    /**
     *
     * @param id
     * @param pageSize
     * @param pageNum
     * @param flag 0 表示当天  1 表示历史
     * @return
     */
    public List<CustomerbillrelatedVo> getCustomerbillrelatedById(Long id, int pageSize, int pageNum, int flag);

    void saveBills(long userId, String orderId, double amount, InOrOutTypeEnum type) throws HzzBizException;
}
