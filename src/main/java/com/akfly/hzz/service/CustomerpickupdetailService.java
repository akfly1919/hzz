package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerpickupdetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户提货明细表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-09
 */
public interface CustomerpickupdetailService extends IService<CustomerpickupdetailVo> {

    void savePickUpDetail(Long cpuiOrderid, Long giiId) throws HzzBizException;

}
