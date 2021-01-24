package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradeorderinfoVo;
import com.akfly.hzz.mapper.TradeorderinfoMapper;
import com.akfly.hzz.service.TradeorderinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易市场买货订单 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradeorderinfoServiceImpl extends ServiceImpl<TradeorderinfoMapper, TradeorderinfoVo> implements TradeorderinfoService {

    @Override
    public List<TradeorderinfoVo> getTradeorderinfoVo(int pageNum, int pageSize, long cbiid, Date beginTime, Date endTime) throws HzzBizException {
        List<TradeorderinfoVo> tradeorderinfoVos = lambdaQuery()
                .eq(TradeorderinfoVo::getTgsBuyerid, cbiid).between(TradeorderinfoVo::getToiTradetime, beginTime, endTime)
                .orderByDesc(TradeorderinfoVo::getToiTradetime).last("limit " + pageNum * pageSize + "," + pageSize + " ").list();

        return tradeorderinfoVos;
    }

    @Override
    public void insertTradeOrder(TradeorderinfoVo vo) throws HzzBizException {

    }

    @Override
    public void updateTradeOrder(TradeorderinfoVo vo) throws HzzBizException {

    }
}
