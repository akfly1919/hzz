package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerpickupdetailMapper;
import com.akfly.hzz.service.CustomerpickupdetailService;
import com.akfly.hzz.vo.CustomerpickupdetailVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户提货明细表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-09
 */
@Service
@Slf4j
public class CustomerpickupdetailServiceImpl extends ServiceImpl<CustomerpickupdetailMapper, CustomerpickupdetailVo> implements CustomerpickupdetailService {

    @Override
    public void savePickUpDetail(Long cpuiOrderid, Long giiId) throws HzzBizException {

        log.info("cpuiOrderid={} giiId={}", cpuiOrderid, giiId);
        if (cpuiOrderid == null || giiId == null) {
            throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
        }
        CustomerpickupdetailVo vo  = new CustomerpickupdetailVo();
        vo.setCpuiOrderid(cpuiOrderid);
        vo.setGiiId(giiId);
        if (!save(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
