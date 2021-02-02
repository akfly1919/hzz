package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerpayinfoMapper;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.service.CustomerpayinfoService;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerbillrelatedVo;
import com.akfly.hzz.vo.CustomerpayinfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>
 * 用户充值信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class CustomerpayinfoServiceImpl extends ServiceImpl<CustomerpayinfoMapper, CustomerpayinfoVo> implements CustomerpayinfoService {

    @Resource
    private CustomerpayinfoMapper customerpayinfoMapper;

    @Resource
    private CustomerbaseinfoService customerbaseinfoService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

    @Override
    public long insertCustomerPayInfo(CustomerpayinfoVo vo) throws HzzBizException {

        try {
            int id = customerpayinfoMapper.insert(vo);
            return vo.getCpiOrderid();
        } catch (Exception e) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }

    @Override
    @Transactional
    public void rechargeSuccess(CustomerpayinfoVo vo) throws HzzBizException {

        try {
            log.info("充值成功入参 vo={}", JsonUtils.toJson(vo));
            CustomerpayinfoVo payInfo = lambdaQuery().eq(CustomerpayinfoVo::getCpiOrderid, vo.getCpiOrderid()).one();
            customerpayinfoMapper.updateById(vo);
            CustomerbaseinfoVo userInfo = customerbaseinfoService.lambdaQuery().eq(CustomerbaseinfoVo::getCbiId, Long.valueOf(payInfo.getCbiId())).one();
            long largeMoney = vo.getCaiAmount() * 1000000;
            userInfo.setCbiBalance(userInfo.getCbiBalance() + largeMoney); // TODO 金额乘以10000

            CustomerbillrelatedVo bill = new CustomerbillrelatedVo();
            bill.setCbiId(Long.valueOf(payInfo.getCbiId()));
            bill.setCbrorderid(String.valueOf(vo.getCpiOrderid()));
            bill.setCbrMoney((double) (largeMoney / 100));
            bill.setCbrType(1); // 1收入  2支出
            Date date = new Date();
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            bill.setCbrCreatetime(instant.atZone(zoneId).toLocalDateTime());
            //bill.setCbrUpdatetime(copy.getCbrUpdatetime());
            customerbillrelatedService.save(bill);
            customerbaseinfoService.updateById(userInfo);
        } catch (Exception e) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
}
