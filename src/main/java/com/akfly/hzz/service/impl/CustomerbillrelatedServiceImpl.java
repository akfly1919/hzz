package com.akfly.hzz.service.impl;

import com.akfly.hzz.mapper.CustomerbillrelatedMapper;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class CustomerbillrelatedServiceImpl extends ServiceImpl<CustomerbillrelatedMapper, CustomerbillrelatedVo> implements CustomerbillrelatedService {

    public List<CustomerbillrelatedVo> getCustomerbillrelatedById(Long id, int pageSize, int pageNum){
        List<CustomerbillrelatedVo> list=lambdaQuery()
                .eq(CustomerbillrelatedVo::getCbiId, id).last("limit " + pageNum + "," + pageSize + " ").list();
        return list;
    }
}
