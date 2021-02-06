package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户地址信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomeraddressinfoService extends IService<CustomeraddressinfoVo> {

    public List<CustomeraddressinfoVo> getAddressInfoById(Long cbiId) throws HzzBizException;

    public void updateAddressInfo(CustomeraddressinfoVo customeraddressinfoVo) throws HzzBizException;

    public void createAddressInfo(CustomeraddressinfoVo customeraddressinfoVo) throws HzzBizException;

    public CustomeraddressinfoVo getDefaultAddressInfoById(Long cbiId) throws HzzBizException;

    public CustomeraddressinfoVo getAddressInfoByCaiId(Long cbiId,Long caiid);
}
