package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.vo.CustomercashoutinfoVo;
import com.akfly.hzz.mapper.CustomercashoutinfoMapper;
import com.akfly.hzz.service.CustomercashoutinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户提现表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomercashoutinfoServiceImpl extends ServiceImpl<CustomercashoutinfoMapper, CustomercashoutinfoVo> implements CustomercashoutinfoService {
    @Override
    public void createcustomercashoutinfo(CustomercashoutinfoVo customercashoutinfoVo) throws HzzBizException {
        if (!save(customercashoutinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
