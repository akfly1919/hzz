package com.akfly.hzz.service.impl;

import com.akfly.hzz.dto.MyTeamDto;
import com.akfly.hzz.vo.CustomerrelationinfoVo;
import com.akfly.hzz.mapper.CustomerrelationinfoMapper;
import com.akfly.hzz.service.CustomerrelationinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户关系表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-28
 */
@Service
public class CustomerrelationinfoServiceImpl extends ServiceImpl<CustomerrelationinfoMapper, CustomerrelationinfoVo> implements CustomerrelationinfoService {

    @Override
    public List<CustomerrelationinfoVo> getCustomerRelations(Long cbiId, int level, int pageSize, int pageNum) {

        List<CustomerrelationinfoVo> list = lambdaQuery().eq(CustomerrelationinfoVo::getCbiId, cbiId)
                .eq(CustomerrelationinfoVo::getCriLevel, level).eq(CustomerrelationinfoVo::getCriIsvalid, 1)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ")
                .list();
        return list;
    }

    @Override
    public List<CustomerrelationinfoVo> getCustomerrelationinfoVo(Long cbiId, int level) {

        List<CustomerrelationinfoVo> list = null;
        if (level == 0) {
            list = lambdaQuery().eq(CustomerrelationinfoVo::getCbiId, cbiId)
                    .in(CustomerrelationinfoVo::getCriLevel, 1, 2).eq(CustomerrelationinfoVo::getCriIsvalid, 1)
                    .list();
        } else {
            list = lambdaQuery().eq(CustomerrelationinfoVo::getCbiId, cbiId)
                    .in(CustomerrelationinfoVo::getCriLevel, level).eq(CustomerrelationinfoVo::getCriIsvalid, 1)
                    .list();
        }
        return list;

    }

    @Override
    public CustomerrelationinfoVo getTeamLevel(Long cbiId) {

        List<CustomerrelationinfoVo> list = lambdaQuery().eq(CustomerrelationinfoVo::getCbiId, cbiId)
                .eq(CustomerrelationinfoVo::getCriIsvalid, 1).last(" limit 1 ")
                .list();
        return list.get(0);
    }
}
