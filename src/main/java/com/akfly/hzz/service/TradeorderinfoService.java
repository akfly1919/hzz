package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradeconfigVo;
import com.akfly.hzz.vo.TradeorderinfoVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易市场买货订单 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TradeorderinfoService extends IService<TradeorderinfoVo> {

    List<TradeorderinfoVo> getTradeorderinfoVo(int pageNum, int pageSize, long cbiid, Date beginTime, Date endTime) throws HzzBizException;

    void insertTradeOrder(TradeorderinfoVo vo) throws HzzBizException;

    void updateTradeOrder(TradeorderinfoVo vo) throws HzzBizException;

    public void nomalBuy(long cbiid,long gbid,int num,double price,boolean isOnSale,int type, int isNew) throws HzzBizException;
    public void dealSold(TradepredictinfoVo tp, TradeconfigVo tc) throws HzzBizException;


    List<TradeorderinfoVo> getBuyNoSpecialTrade(long userid);
}
