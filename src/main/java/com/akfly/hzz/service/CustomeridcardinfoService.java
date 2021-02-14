package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomeridcardinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户身份证信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomeridcardinfoService extends IService<CustomeridcardinfoVo> {

    void saveCardInfo(long userId, String idCardFront, String idCardBack) throws HzzBizException;

    CustomeridcardinfoVo getCardInfo(long userId) throws HzzBizException;

    void saveOrUpdateIdCard(CustomeridcardinfoVo vo) throws HzzBizException;

    void updateByUserId(long userId) throws HzzBizException;
}
