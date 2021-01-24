package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.mapper.CustomeraddressinfoMapper;
import com.akfly.hzz.service.CustomeraddressinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 用户地址信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomeraddressinfoServiceImpl extends ServiceImpl<CustomeraddressinfoMapper, CustomeraddressinfoVo> implements CustomeraddressinfoService {
    @Override
    public List<CustomeraddressinfoVo> getAddressInfoById(Long cbiId) throws HzzBizException {

        List<CustomeraddressinfoVo> adresslist = lambdaQuery()
                .eq(CustomeraddressinfoVo::getCbiId, cbiId).list();
        return adresslist;
    }
    @Override
    public void createAddressInfo(CustomeraddressinfoVo customeraddressinfoVo) throws HzzBizException {
        if (!save(customeraddressinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
    @Override
    public void updateAddressInfo(CustomeraddressinfoVo customeraddressinfoVo) throws HzzBizException {

        if (!updateById(customeraddressinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
