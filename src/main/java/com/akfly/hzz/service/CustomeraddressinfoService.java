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
    public List<CustomeraddressinfoVo> getUserInfoById(String cbiId) throws HzzBizException;
}
