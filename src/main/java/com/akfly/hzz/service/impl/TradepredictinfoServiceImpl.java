package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.service.TradetimeService;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import com.akfly.hzz.mapper.TradepredictinfoMapper;
import com.akfly.hzz.service.TradepredictinfoService;
import com.akfly.hzz.vo.TradetimeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 交易预购买表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradepredictinfoServiceImpl extends ServiceImpl<TradepredictinfoMapper, TradepredictinfoVo> implements TradepredictinfoService {

    @Resource
    TradetimeService tradetimeService;
    @Resource
    private CustomerbaseinfoMapper customerbaseinfoMapper;
    @Resource
    private CustomerbaseinfoService customerbaseinfoService;

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;


    public void saveTradepredictinfoVo(TradepredictinfoVo tradepredictinfoVo) throws HzzBizException {
       if(!saveOrUpdate(tradepredictinfoVo)) {
           throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
       }
    }

    @Override
    public List<TradepredictinfoVo> getBuyTrade(long userId, int pageSize, int pageNum) throws HzzBizException {

        List<TradepredictinfoVo> list = lambdaQuery().eq(TradepredictinfoVo::getTpiBuyerid, userId)
                .orderByDesc(TradepredictinfoVo::getTpiCreatetime)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        return list;
    }
    //@Transactional(rollbackFor = Exception.class)
    public int releas(int num) throws HzzBizException {
        TradetimeVo tt = tradetimeService.getTradeTime();
        String date=DateUtil.getCurrentDate(DateUtil.FORMAT_DATE);
        String datetime=date+" "+tt.getTtTimePmEnd();
        LocalDateTime ld=LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("tpi_status",1);
        wrapper.le("tpi_createtime",ld);
        wrapper.orderByAsc("tpi_createtime");
        wrapper.last("limit "+num+" for update");
        List<TradepredictinfoVo> list = getBaseMapper().selectList(wrapper);
        if(list==null||list.size()==0){
            return 0;
        }
        for(TradepredictinfoVo tpi:list){
            try{
            releaseOne(tpi.getTpiId(),5);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list.size();
    }

    public  void cancel(String tpiid) throws HzzBizException {
        releaseOne(tpiid,2);
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseOne(String tpiid,int status) throws HzzBizException {
        TradepredictinfoVo tpi= lambdaQuery().eq(TradepredictinfoVo::getTpiId,tpiid).one();
        if (tpi==null){
            throw new HzzBizException(HzzExceptionEnum.ORDER_NOTEXIST_ERROR);
        }
        if(tpi.getTpiStatus() != 1){
            //状态已变，无需处理
            return false;
        }
        CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(tpi.getTpiBuyerid());
        Double balance=customerbaseinfoVo.getCbiBalance();
        Double fronze=customerbaseinfoVo.getCbiFrozen();
        BigDecimal balanceB=BigDecimal.valueOf(balance!=null?balance:0);
        BigDecimal fronzeB=BigDecimal.valueOf(fronze!=null?fronze:0);
        int successNum = tpi.getTpiSucessnum();
        int leftnum = tpi.getTpiNum() - successNum;
        BigDecimal priceB=BigDecimal.valueOf(tpi.getTpiPrice()).multiply(BigDecimal.valueOf(leftnum));
        BigDecimal feeB;
        if (successNum == 0) {
            feeB=BigDecimal.valueOf(tpi.getTpiServicefee());
        } else {
            GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(tpi.getGbiId());
            feeB=BigDecimal.valueOf(goods.getGbiBuyservicerate()).multiply(BigDecimal.valueOf(leftnum)).multiply(BigDecimal.valueOf(tpi.getTpiPrice()));
        }
        balanceB=balanceB.add(feeB).add(priceB);
        fronzeB=fronzeB.subtract(feeB).subtract(priceB);
        customerbaseinfoVo.setCbiFrozen(fronzeB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        customerbaseinfoVo.setCbiBalance(balanceB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
        if(leftnum==tpi.getTpiNum()){
            tpi.setTpiStatus(status);
        }else {
            if(status==2){
                tpi.setTpiStatus(6);
            }else{
                tpi.setTpiStatus(3);
            }
        }
        tpi.setTpiFinishtime(LocalDateTime.now());
        saveTradepredictinfoVo(tpi);
        return true;
    }

    @Override
    public List<TradepredictinfoVo> getBuyDetails(long gbiId, int pageSize, int pageNum) throws HzzBizException {

        List<TradepredictinfoVo> list = lambdaQuery().eq(TradepredictinfoVo::getGbiId, gbiId)
                .le(TradepredictinfoVo::getTpiBuytime, LocalDateTime.now())
                .ge(TradepredictinfoVo::getTpiFinishtime, LocalDateTime.now())
                .in(TradepredictinfoVo::getTpiGoodstype, 1, 2)
                .orderByDesc(TradepredictinfoVo::getTpiCreatetime)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        return list;

    }

}
