package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import com.akfly.hzz.mapper.CustomerpayinfoMapper;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private CustomerpayinfoMapper customerpayinfoMapper;

    @Override
    public long insertCustomerPayInfo(CustomerpayinfoVo vo) throws HzzBizException {

        try {
            int id = customerpayinfoMapper.insert(vo);
            return vo.getCpiOrderid();
        } catch (Exception e) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
}
