package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.mapper.CustomeraddressinfoMapper;
import com.akfly.hzz.service.CustomeraddressinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public CustomeraddressinfoVo getAddressInfoByCaiId(Long cbiId,Long caiid) {

        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.eq("cai_id",caiid);
        wrapper_c.eq("cbi_id",cbiId);
        wrapper_c.eq("cai_valid",1);
        return  getBaseMapper().selectOne(wrapper_c);
    }
    public CustomeraddressinfoVo getDefaultAddressInfoById(Long cbiId) throws HzzBizException {
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.eq("cbi_id",cbiId);
        wrapper_c.eq("cai_valid",1);
        wrapper_c.orderByAsc("cai_sort");
        wrapper_c.orderByDesc("cai_updatetime");
        wrapper_c.last("limit 1");
        CustomeraddressinfoVo defaultA= getBaseMapper().selectOne(wrapper_c);
        if(defaultA==null){
            throw  new HzzBizException(HzzExceptionEnum.GOODS_DEFAULT_ADDRESS_ERROR);
        }
        return defaultA;
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
