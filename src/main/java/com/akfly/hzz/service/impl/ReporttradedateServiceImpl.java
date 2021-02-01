package com.akfly.hzz.service.impl;

import com.akfly.hzz.mapper.ReporttradedateMapper;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

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
}
