package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomercardinfoVo;
import com.akfly.hzz.mapper.CustomercardinfoMapper;
import com.akfly.hzz.service.CustomercardinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomercardinfoServiceImpl extends ServiceImpl<CustomercardinfoMapper, CustomercardinfoVo> implements CustomercardinfoService {

    @Override
    public void saveCardInfo(String userId, String idCardFront, String idCardBack) throws HzzBizException {

        CustomercardinfoVo vo = new CustomercardinfoVo();

    }

    @Override
    public void getCardInfo(String userId) throws HzzBizException {

    }
}
