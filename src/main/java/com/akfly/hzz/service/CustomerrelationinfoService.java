package com.akfly.hzz.service;

import com.akfly.hzz.dto.MyTeamDto;
import com.akfly.hzz.vo.CustomerrelationinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户关系表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-28
 */
public interface CustomerrelationinfoService extends IService<CustomerrelationinfoVo> {

    List<CustomerrelationinfoVo> getCustomerRelations(Long cbiId, int level, int pageSize, int pageNum);

    /**
     *
     * @param cbiId
     * @param level 0:全部 1:1级 2:2级
     * @return
     */
    List<CustomerrelationinfoVo> getCustomerrelationinfoVo(Long cbiId, int level);

    CustomerrelationinfoVo getTeamLevel(Long cbiId);

}
