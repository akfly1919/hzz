package com.akfly.hzz.service.impl;

import com.akfly.hzz.mapper.ReporttradedateMapper;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易行情日期 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-01
 */
@Service
public class ReporttradedateServiceImpl extends ServiceImpl<ReporttradedateMapper, ReporttradedateVo> implements ReporttradedateService {


    @Override
    public int getRtiNum(long gbiId) {

        int rtiNum = lambdaQuery().eq(ReporttradedateVo::getRtiGbid, gbiId).count();
        return rtiNum;
    }
}
