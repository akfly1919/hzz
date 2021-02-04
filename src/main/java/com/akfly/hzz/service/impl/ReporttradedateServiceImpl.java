package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.ReporttradedateMapper;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 交易行情日期 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-01
 */
@Service
@Slf4j
public class ReporttradedateServiceImpl extends ServiceImpl<ReporttradedateMapper, ReporttradedateVo> implements ReporttradedateService {

    @Resource
    private ReporttradedateMapper reporttradedateMapper;

    public void saveReporttradedateVo(ReporttradedateVo reporttradedateVo) throws HzzBizException {
        if(!saveOrUpdate(reporttradedateVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    @Override
    public int getRtiNum(long gbiId) {

        int total_num = 0;
        try {
            QueryWrapper<ReporttradedateVo> contract_wrapper = new QueryWrapper<ReporttradedateVo>();
            contract_wrapper.eq("rti_gbid", gbiId);
            contract_wrapper.select("ifnull(sum(rti_num),0) as total_num ");
            Map<String, Object> map = getMap(contract_wrapper);
            total_num = Integer.parseInt(String.valueOf(map.get("total_num")));
        } catch (Exception e) {
            log.error("查询交易行情数据数据库异常", e);
        }
        return total_num;
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
            queryWrapper.orderByDesc("rti_year,rti_month");
            queryWrapper.last("limit 12");
        }else if("DAY".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_month,rti_date,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_month,rti_date");
            queryWrapper.orderByDesc("rti_year,rti_month,rti_date");
            queryWrapper.last("limit 30");
        }else if("WEEK".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_week,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_week");
            queryWrapper.orderByDesc("rti_year,rti_week");
            queryWrapper.last("limit 52");
        }else if("HOUR".equalsIgnoreCase(queryType)){
            queryWrapper.select(" rti_gbid,rti_year, rti_month, rti_date,rti_hour,sum(rti_num) as rti_num,sum(rti_money) as rti_money ");
            queryWrapper.groupBy("rti_year,rti_month,rti_date,rti_hour");
            queryWrapper.orderByDesc("rti_year,rti_month,rti_date,rti_hour");
            queryWrapper.last("limit 24");
        }
        List<ReporttradedateVo> list=baseMapper.selectList(queryWrapper);
        return list;
    }
}
