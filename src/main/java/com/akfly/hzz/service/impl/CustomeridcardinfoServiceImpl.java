package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomeridcardinfoVo;
import com.akfly.hzz.mapper.CustomeridcardinfoMapper;
import com.akfly.hzz.service.CustomeridcardinfoService;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户身份证信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class CustomeridcardinfoServiceImpl extends ServiceImpl<CustomeridcardinfoMapper, CustomeridcardinfoVo> implements CustomeridcardinfoService {

    @Resource
    private CustomeridcardinfoMapper customeridcardinfoMapper;

    @Override
    public void saveCardInfo(long userId, String idCardFront, String idCardBack) throws HzzBizException {

        CustomeridcardinfoVo vo = new CustomeridcardinfoVo();
        vo.setCbiId(userId);
        vo.setCiiIdcardback(idCardBack);
        vo.setCiiIdcardfront(idCardFront);
        vo.setCiiOperator("SYSTEM");
        vo.setCiiStatus(2); // 未审核
        if (!saveOrUpdate(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    @Override
    public CustomeridcardinfoVo getCardInfo(long userId) throws HzzBizException {

        List<CustomeridcardinfoVo> idCard = lambdaQuery()
                .eq(CustomeridcardinfoVo::getCbiId, userId).list();
        if (CollectionUtils.isEmpty(idCard)) {
            throw new HzzBizException(HzzExceptionEnum.USER_NOT_REALNAME);
        }
        return idCard.get(0);

    }

    @Override
    public void saveOrUpdateIdCard(CustomeridcardinfoVo vo) throws HzzBizException {

        if (!saveOrUpdate(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    public void updateByUserId(long userId) throws HzzBizException {

        try {
            LambdaUpdateChainWrapper<CustomeridcardinfoVo> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(customeridcardinfoMapper);
            lambdaUpdateChainWrapper.eq(CustomeridcardinfoVo::getCbiId, userId).set(CustomeridcardinfoVo::getCiiStatus, 2).update();
        } catch (Exception e) {
            log.error("更新用户实名信息数据库异常 userId={}", userId, e);
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

}
