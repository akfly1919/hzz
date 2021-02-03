package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradetimeVo;
import com.akfly.hzz.mapper.TradetimeMapper;
import com.akfly.hzz.service.TradetimeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 交易时间配置 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradetimeServiceImpl extends ServiceImpl<TradetimeMapper, TradetimeVo> implements TradetimeService {

    @Resource
    TradetimeMapper tradetimeMapper;
    public TradetimeVo getTradeTime() throws HzzBizException {
        QueryWrapper<TradetimeVo> contract_wrapper = new QueryWrapper<TradetimeVo>();
        contract_wrapper.eq("tt_status","1");
        TradetimeVo tt= tradetimeMapper.selectOne(contract_wrapper);
        if(tt==null){
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
        return tt;
    }
}
