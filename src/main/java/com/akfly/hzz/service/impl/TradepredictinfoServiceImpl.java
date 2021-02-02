package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.akfly.hzz.mapper.TradepredictinfoMapper;
import com.akfly.hzz.service.TradepredictinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易预购买表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradepredictinfoServiceImpl extends ServiceImpl<TradepredictinfoMapper, TradepredictinfoVo> implements TradepredictinfoService {

    public void saveTradepredictinfoVo(TradepredictinfoVo tradepredictinfoVo) throws HzzBizException {
       if(!saveOrUpdate(tradepredictinfoVo)) {
           throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
       }
    }
}
