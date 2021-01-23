package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomercardinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户账户信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomercardinfoService extends IService<CustomercardinfoVo> {


    void saveCardInfo(String userId, String idCardFront, String idCardBack) throws HzzBizException;

    void getCardInfo(String userId) throws HzzBizException;

}
