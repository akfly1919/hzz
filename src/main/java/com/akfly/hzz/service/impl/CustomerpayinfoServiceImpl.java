package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import com.akfly.hzz.mapper.CustomerpayinfoMapper;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户充值信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomerpayinfoServiceImpl extends ServiceImpl<CustomerpayinfoMapper, CustomerpayinfoVo> implements CustomerpayinfoService {

    @Override
    public void insertCustomerPayInfo(CustomerpayinfoVo vo) throws HzzBizException {

        if (!save(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
