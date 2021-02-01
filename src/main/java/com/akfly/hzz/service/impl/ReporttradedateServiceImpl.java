package com.akfly.hzz.service.impl;

import com.akfly.hzz.mapper.ReporttradedateMapper;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ReporttradedateVo> listReporttradedateStatistics(long gbid,String queryType){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("rti_gbid",gbid);

        if("YEAR".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year");
        }else if("MONTH".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_month,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_month");
        }else if("DAY".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_month,rti_date,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_month,rti_date");
        }else if("WEEK".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_week,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_week");
        }else if("HOUR".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_month, rti_date,rti_hour,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_month,rti_date,rti_hour");
        }
        List<ReporttradedateVo> list=baseMapper.selectList(queryWrapper);
        return list;
    }
}
