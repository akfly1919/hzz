package com.akfly.hzz.service;


import com.akfly.hzz.constant.CbrClassEnum;
import com.akfly.hzz.constant.InOrOutTypeEnum;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
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

    void saveBills(long userId, String orderId, double amount, InOrOutTypeEnum type, CbrClassEnum cbrClass) throws HzzBizException;

    /**
     * 汇总商品值和累计佣金
     * @param userId
     * @param cbrClass  10 商品值 11 佣金
     * @return
     */
    BigDecimal sumAmount(long userId, int cbrClass);

    /**
     * 根据用户id和type获取账单流水
     * @param userId
     * @param pageSize
     * @param pageNum
     * @param cbrClass 10 商品值 11 佣金
     * @return
     */
    List<CustomerbillrelatedVo> getCustomerbillById(Long userId, int pageSize, int pageNum, int cbrClass);


}
