package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.TradeconfigVo;
import com.akfly.hzz.mapper.TradeconfigMapper;
import com.akfly.hzz.service.TradeconfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易配置信息 如费率等 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradeconfigServiceImpl extends ServiceImpl<TradeconfigMapper, TradeconfigVo> implements TradeconfigService {

    public TradeconfigVo getTradeconfig(int type) throws HzzBizException {
        QueryWrapper<TradeconfigVo> wrapper=new QueryWrapper<TradeconfigVo>();
        wrapper.eq("tc_type",type);
        wrapper.eq("tc_valid",1);
        TradeconfigVo tc = getBaseMapper().selectOne(wrapper);
        if(tc==null){
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
        return tc;
    }
}
