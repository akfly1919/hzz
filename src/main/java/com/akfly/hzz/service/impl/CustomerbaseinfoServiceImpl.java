package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.EncryDecryUtils;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RedisUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZoneId;
import java.util.Date;
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
@Slf4j
public class CustomerbaseinfoServiceImpl extends ServiceImpl<CustomerbaseinfoMapper, CustomerbaseinfoVo> implements CustomerbaseinfoService {

    @Autowired
    private RedisUtils redisUtils;

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

        String key = CommonConstant.MSG_CODE_PREFIX + phoneNum;
        String redisCode = String.valueOf(redisUtils.get(key));
        if (!msgCode.equals(redisCode)) {
            throw new HzzBizException(HzzExceptionEnum.MSG_CODE_INVALID);
        } else {
            redisUtils.del(key); // 使用过之后就删除
        }
        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiPhonenum, phoneNum).list();
        if (CollectionUtils.isEmpty(users)) {
            userRegister(phoneNum, null, null);
            return getUserInfo(phoneNum);
        } else {
            return users.get(0);
        }
    }

    @Override
    public void userRegisterByCode(String phoneNum, String msgCode) throws HzzBizException {

        CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
        vo.setCbiPhonenum(phoneNum);
        if (!save(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    @Override
    public void userRegister(String phoneNum, String psw, String invitationCode) throws HzzBizException {

        CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
        vo.setCbiPhonenum(phoneNum);
        vo.setCbiPassword(psw);
        vo.setCbiType(2);
        vo.setCbiName(phoneNum);
        vo.setCbiUsername(phoneNum);
        CustomerbaseinfoVo parentVo = getUserInfoByInvitationCode(invitationCode);
        if (parentVo != null) {
            vo.setCbiParentid(parentVo.getCbiId());
        }
        if (!save(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    public void updateUserInfo(CustomerbaseinfoVo customerbaseinfoVo) throws HzzBizException {

        if (!updateById(customerbaseinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    public CustomerbaseinfoVo getUserInfo(String phoneNum) throws HzzBizException {

        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiPhonenum, phoneNum).list();
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public CustomerbaseinfoVo getUserInfoById(String cbiId) throws HzzBizException {

        String key = CommonConstant.USER_PREFIX + cbiId;
        Object o = redisUtils.get(key);

        if (o == null) {
            List<CustomerbaseinfoVo> users = lambdaQuery()
                    .eq(CustomerbaseinfoVo::getCbiId, cbiId).list();
            if (CollectionUtils.isEmpty(users)) {
                throw new HzzBizException(HzzExceptionEnum.USER_NOTEXIST_ERROR);
            }

            CustomerbaseinfoVo vo = users.get(0);
            redisUtils.set(key, JsonUtils.toJson(users.get(0)), 2 * 60 *60);
            return users.get(0);
        } else {
            log.warn("从redis获取用户信息vo={}", JsonUtils.toJson(o));
            CustomerbaseinfoVo vo = JsonUtils.toBean(o.toString(), CustomerbaseinfoVo.class);
            //CustomerbaseinfoVo vo = JSONObject.parse().decode(o.toString(), CustomerbaseinfoVo.class);
            //CustomerbaseinfoVo vo = JSONObject.parseObject(o.toString(), CustomerbaseinfoVo.class);
            return vo;
        }
    }

    @Override
    public Long getInvitationCode(long cbiId) throws HzzBizException {


        return null;
    }


    private CustomerbaseinfoVo getUserInfoByInvitationCode(String invitationCode) {

        if (StringUtils.isBlank(invitationCode)) {
            return null;
        }
        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiShareurl, invitationCode).list();
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

}
