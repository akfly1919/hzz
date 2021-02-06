package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    public void sell(long cbiid,long gbid,int num,double price) throws HzzBizException;

    List<TradegoodsellVo> getSellTrade(long userId, int pageSize, int pageNum) throws HzzBizException;

    int getSellVolume(long gbid);
}
