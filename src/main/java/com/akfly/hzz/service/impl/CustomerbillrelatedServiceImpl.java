package com.akfly.hzz.service.impl;

import com.akfly.hzz.mapper.CustomerbillrelatedMapper;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户账单流水对应表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-24
 */
@Service
@Slf4j
public class CustomerbillrelatedServiceImpl extends ServiceImpl<CustomerbillrelatedMapper, CustomerbillrelatedVo> implements CustomerbillrelatedService {

    public List<CustomerbillrelatedVo> getCustomerbillrelatedById(Long id, int pageSize, int pageNum, int flag){

        List<CustomerbillrelatedVo> list = null;
        if (flag == 1) {
            Date end = DateUtil.getDateBegin(new Date());
            list = lambdaQuery()
                    .eq(CustomerbillrelatedVo::getCbiId, id).lt(CustomerbillrelatedVo::getCbrCreatetime, end).last("limit " + pageNum + "," + pageSize + " ").list();
        } else if (flag == 0) {
            Date begin = DateUtil.getDateBegin(new Date());
            Date end = DateUtils.addDays(begin, 1);
            log.info("begin={}, end={}", begin, end);
            list = lambdaQuery()
                    .eq(CustomerbillrelatedVo::getCbiId, id).between(CustomerbillrelatedVo::getCbrCreatetime, begin, end).last("limit " + pageNum + "," + pageSize + " ").list();
        }
        return list;
    }
}
