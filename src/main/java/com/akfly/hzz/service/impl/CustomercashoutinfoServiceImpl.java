package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomercashoutinfoVo;
import com.akfly.hzz.mapper.CustomercashoutinfoMapper;
import com.akfly.hzz.service.CustomercashoutinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    CustomerbaseinfoService customerbaseinfoService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createcustomercashoutinfo(CustomercashoutinfoVo customercashoutinfoVo) throws HzzBizException {
        /*String locakKey = "";
        long threadId = Thread.currentThread().getId();
        long timeStart = System.currentTimeMillis();
        try {
            locakKey = customercashoutinfoVo.getCbiId().toString();
            //使用可重入锁;获取锁后10秒自动释放锁
            try {
                distributedLocker.lock(locakKey, 10);
            } catch (Throwable e) {
            }*/
            CustomerbaseinfoVo customerbaseinfoVo=customerbaseinfoService.getUserInfoByIdForUpdate(customercashoutinfoVo.getCbiId().toString());
            Double balance=customerbaseinfoVo.getCbiBalance();
            Double fronze=customerbaseinfoVo.getCbiFrozen();
            Double amount=customercashoutinfoVo.getCcoiAmount();
            BigDecimal balanceB=new BigDecimal(balance!=null?balance:new Double(0.0));
            BigDecimal fronzeB=new BigDecimal(fronze!=null?fronze:new Double(0.0));
            BigDecimal amountB=new BigDecimal(amount!=null?amount:new Double(0.0));
            if(balanceB.compareTo(amountB)<0){
                throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
            }
            customerbaseinfoVo.setCbiFrozen(fronzeB.add(amountB).doubleValue());
            customerbaseinfoVo.setCbiBalance(balanceB.subtract(amountB).doubleValue());
            customerbaseinfoVo.setCbiUpdatetime(LocalDateTime.now());
            customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
            if (!save(customercashoutinfoVo)) {
                throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
            }
       /* }finally {
            distributedLocker.unlock(locakKey,threadId,timeStart);
        }*/

    }

    @Override
    public List<CustomercashoutinfoVo> getWithdraw(Long userId, int pageSize, int pageNum) throws HzzBizException {

        List<CustomercashoutinfoVo> list = lambdaQuery().eq(CustomercashoutinfoVo::getCbiId, userId)
                .last("limit " + pageNum + "," + pageSize + " ").list();
        return list;
    }
}
