package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.EncryDecryUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 用户基础信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomerbaseinfoServiceImpl extends ServiceImpl<CustomerbaseinfoMapper, CustomerbaseinfoVo> implements CustomerbaseinfoService {


    @Override
    public CustomerbaseinfoVo userLoginByPsw(String phoneNum, String psw) throws HzzBizException {

        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiPhonenum, phoneNum)
                .eq(CustomerbaseinfoVo::getCbiPassword, psw).list();
        if (CollectionUtils.isEmpty(users)) {
            throw new HzzBizException(HzzExceptionEnum.NAME_OR_PSW_ERROR);
        }
        return users.get(0);
    }

    @Override
    public CustomerbaseinfoVo userLoginByCode(String phoneNum, String msgCode) throws HzzBizException {

        // TODO 从redis里面获取code跟msgCode比较
        String redisCode = "";
        if (!msgCode.equals(redisCode)) {
            throw new HzzBizException(HzzExceptionEnum.NAME_OR_PSW_ERROR);
        }
        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiPhonenum, phoneNum).list();
        if (CollectionUtils.isEmpty(users)) {
            throw new HzzBizException(HzzExceptionEnum.NAME_OR_PSW_ERROR);
        }
        return users.get(0); // TODO 后面需要放到redis里面
    }

    @Override
    public void userRegisterByCode(String phoneNum, String psw) throws HzzBizException {

    }

    @Override
    public void userRegisterByPsw(String phoneNum, String psw) throws HzzBizException {

    }

    @Override
    public void updateUserInfo(CustomerbaseinfoVo customerbaseinfoVo) throws HzzBizException {

    }

    @Override
    public CustomerbaseinfoVo getUserInfo(String userToken) throws HzzBizException {
        return null;
    }

}
