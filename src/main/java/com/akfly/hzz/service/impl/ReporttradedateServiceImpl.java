package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.akfly.hzz.mapper.ReporttradedateMapper;
import com.akfly.hzz.service.ReporttradedateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 交易行情日期 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-30
 */
@Service
public class ReporttradedateServiceImpl extends ServiceImpl<ReporttradedateMapper, ReporttradedateVo> implements ReporttradedateService {


    @Override
    public int getRtiNum(long gbiId) {

        int rtiNum = lambdaQuery().eq(ReporttradedateVo::getRtiGbid, gbiId).count();
        return rtiNum;
    }
}
