package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.akfly.hzz.service.TradegoodsellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易市场卖货订 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradegoodsellServiceImpl extends ServiceImpl<TradegoodsellMapper, TradegoodsellVo> implements TradegoodsellService {

    public void saveTradegoodsell(TradegoodsellVo tradegoodsellVo) throws HzzBizException {
        if(!saveOrUpdate(tradegoodsellVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
}
