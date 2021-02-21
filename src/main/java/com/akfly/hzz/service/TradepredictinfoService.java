package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 交易预购买表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TradepredictinfoService extends IService<TradepredictinfoVo> {
    public void saveTradepredictinfoVo(TradepredictinfoVo tradepredictinfoVo) throws HzzBizException;

    List<TradepredictinfoVo> getBuyTrade(long userId, int pageSize, int pageNum) throws HzzBizException;
    public int releas(int num) throws HzzBizException;
    public  void cancel(String orderid) throws HzzBizException;
    public boolean releaseOne(String tpiid,int status) throws HzzBizException;

    /**
     * 获取正在挂单预购买的交易明细
     * @param gbiId
     * @param pageSize
     * @param pageNum
     * @return
     * @throws HzzBizException
     */
    List<TradepredictinfoVo> getBuyDetails(long gbiId, int pageSize, int pageNum) throws HzzBizException;
}
