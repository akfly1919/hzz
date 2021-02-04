package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户充值信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomerpayinfoService extends IService<CustomerpayinfoVo> {


    long insertCustomerPayInfo(CustomerpayinfoVo vo) throws HzzBizException;

    void rechargeSuccess(CustomerpayinfoVo vo, String rechargeAmount) throws HzzBizException;

}
