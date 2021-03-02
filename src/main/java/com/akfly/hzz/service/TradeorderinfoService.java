package com.akfly.hzz.service;

import com.akfly.hzz.dto.HistoryTradeDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.TradeconfigVo;
import com.akfly.hzz.vo.TradeorderinfoVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
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

    void nomalBuy(CustomerbaseinfoVo userInfo, long gbid, int num, double price, boolean isOnSale, int type) throws HzzBizException;

    void dealSold(TradepredictinfoVo tp, TradeconfigVo tc, boolean isOnSale) throws HzzBizException;


    List<TradeorderinfoVo> getBuyNoSpecialTrade(long userid);

    int getActiveUser(List<Long> userIds);

    /**
     *
     * @param userIds
     * @param flag 0: 普通商品和新手商品  1: 特价商品
     * @param flag 0: 买  1: 卖
     * @return
     */
    BigDecimal getAmount(List<Long> userIds, int flag, int direction);


    HistoryTradeDto getSumAmount(Long userIds, boolean isCurrentDay);
}
