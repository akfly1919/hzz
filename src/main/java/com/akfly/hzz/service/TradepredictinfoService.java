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
}
