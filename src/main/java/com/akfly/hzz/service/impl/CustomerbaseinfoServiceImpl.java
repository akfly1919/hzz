package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.UserAllAssetDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RedisUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    @Resource
    private CustomerbaseinfoMapper customerbaseinfoMapper;

    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

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

        String key = CommonConstant.MSG_CODE_LOGIN + phoneNum;
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
        vo.setCbiTotal(0d);
        vo.setCbiBalance(0d);
        vo.setCbiFrozen(0d);
        CustomerbaseinfoVo parentVo = getUserInfoByInvitationCode(invitationCode);
        if (parentVo != null) {
            vo.setCbiJointime(LocalDateTime.now());
            vo.setCbiParentid(parentVo.getCbiId());
            vo.setCbiShareurl(invitationCode);
        } else {
            throw new HzzBizException(HzzExceptionEnum.INVITATION_INVALID);
        }
        if (!save(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    public void updateUserInfo(CustomerbaseinfoVo customerbaseinfoVo) throws HzzBizException {

        if (!updateById(customerbaseinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        } else {
            String key = CommonConstant.USER_PREFIX + customerbaseinfoVo.getCbiId();
            CustomerbaseinfoVo userInDB = getUserInfo(customerbaseinfoVo.getCbiPhonenum());
            redisUtils.set(key, JsonUtils.toJson(userInDB), 30 *60);
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
            redisUtils.set(key, JsonUtils.toJson(vo), 30 *60);
            return vo;
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

    @Override
    public CustomerbaseinfoVo getUserInfoByIdForUpdate(String cbiId) throws HzzBizException {
        return customerbaseinfoMapper.selectByIdForUpdate(cbiId);
    }


    private CustomerbaseinfoVo getUserInfoByInvitationCode(String invitationCode) {

        if (StringUtils.isBlank(invitationCode)) {
            return null;
        }
        try {
            long code = Long.parseLong(invitationCode);
            long userId = (code - 1) >> 1;
            List<CustomerbaseinfoVo> users = lambdaQuery()
                    .eq(CustomerbaseinfoVo::getCbiId, userId).list();
            if (CollectionUtils.isEmpty(users)) {
                return null;
            }
            return users.get(0);
        } catch (Exception e) {
            log.error("根据邀请码获取用户信息异常 invitationCode={}", invitationCode, e);
            return null;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void frozenAccount(long cbiid,Double total) throws HzzBizException {

        log.info("冻结用户余额cbiid={} total={}", cbiid, total);
        CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(cbiid);
        Double balance=customerbaseinfoVo.getCbiBalance();
        Double fronze=customerbaseinfoVo.getCbiFrozen();
        BigDecimal balanceB=BigDecimal.valueOf(balance!=null?balance:0);
        BigDecimal fronzeB=BigDecimal.valueOf(fronze!=null?fronze:0);
        BigDecimal totalB=BigDecimal.valueOf(total);
        customerbaseinfoVo.setCbiBalance(balanceB.subtract(totalB).doubleValue());
        customerbaseinfoVo.setCbiFrozen(fronzeB.add(totalB).doubleValue());
        customerbaseinfoVo.setCbiUpdatetime(LocalDateTime.now());
        if(balanceB.compareTo(new BigDecimal("0.0"))<0){
            throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
        }
        updateUserInfo(customerbaseinfoVo);
    }

    @Override
    public CustomerbaseinfoVo getUserInfoByIdInDb(long cbiId) throws HzzBizException {

        List<CustomerbaseinfoVo> users = lambdaQuery()
                .eq(CustomerbaseinfoVo::getCbiId, cbiId).list();
        if (CollectionUtils.isEmpty(users)) {
            throw new HzzBizException(HzzExceptionEnum.USER_NOTEXIST_ERROR);
        }
        CustomerbaseinfoVo vo = users.get(0);
        UserAllAssetDto dto = customergoodsrelatedService.getMyAsset(cbiId);
        vo.setStock(dto.getStock());
        BigDecimal total = dto.getAsset().add(BigDecimal.valueOf(vo.getCbiTotal()));
        BigDecimal balance = dto.getAsset().add(BigDecimal.valueOf(vo.getCbiBalance()));
        vo.setCbiTotal(total.doubleValue());
        vo.setCbiBalance(balance.doubleValue());
        BigDecimal goodsAmount = customerbillrelatedService.sumAmount(cbiId, 10);
        BigDecimal commissionAmount = customerbillrelatedService.sumAmount(cbiId, 11);
        vo.setGoodsAmount(goodsAmount);
        vo.setCommissionAmount(commissionAmount);
        return vo;
    }

    @Override
    public void addInvitationCode(String invitationCode, Long userId) throws HzzBizException {

        CustomerbaseinfoVo userInfo = lambdaQuery().eq(CustomerbaseinfoVo::getCbiId, userId).one();
        if (userInfo == null || StringUtils.isNotBlank(userInfo.getCbiShareurl()))
            throw new HzzBizException(HzzExceptionEnum.HAD_SET_INVITATION);

        CustomerbaseinfoVo parent = getUserInfoByInvitationCode(invitationCode);
        if (parent == null) {
            throw new HzzBizException(HzzExceptionEnum.INVITATION_INVALID);
        }
        CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
        vo.setCbiId(userId);
        vo.setCbiShareurl(invitationCode);
        vo.setCbiJointime(LocalDateTime.now());
        vo.setCbiParentid(parent.getCbiId());
        if (!updateById(vo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
}
