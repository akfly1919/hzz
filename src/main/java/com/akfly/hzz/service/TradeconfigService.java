package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradeconfigVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易配置信息 如费率等 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TradeconfigService extends IService<TradeconfigVo> {
    public TradeconfigVo getTradeconfig(int type) throws HzzBizException;
}
