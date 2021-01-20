package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基础信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomerbaseinfoService extends IService<CustomerbaseinfoVo> {

    String userLoginByPsw(String phoneNum, String psw) throws HzzBizException;

    String userLoginByCode(String phoneNum, String msgCode) throws HzzBizException;

    void userRegisterByCode(String phoneNum, String psw) throws HzzBizException;

    void userRegisterByPsw(String phoneNum, String psw) throws HzzBizException;

    void updateUserInfo(CustomerbaseinfoVo customerbaseinfoVo) throws HzzBizException;

    CustomerbaseinfoVo getUserInfo(String userToken) throws HzzBizException;
}
