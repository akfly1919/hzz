package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易市场卖货订 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TradegoodsellService extends IService<TradegoodsellVo> {
    public void saveTradegoodsell(TradegoodsellVo tradegoodsellVo) throws HzzBizException;
}
