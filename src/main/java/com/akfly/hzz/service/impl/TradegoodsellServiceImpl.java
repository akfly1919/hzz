package com.akfly.hzz.service.impl;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.TradepredictinfoMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.*;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交易市场卖货订 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class TradegoodsellServiceImpl extends ServiceImpl<TradegoodsellMapper, TradegoodsellVo> implements TradegoodsellService {

    @Resource
    private CustomergoodsrelatedMapper customergoodsrelatedMapper;
    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;
    @Resource
    private TradeconfigService tradeconfigService;
    @Resource
    GoodsbaseinfoService goodsbaseinfoService;
    @Resource
    private TradetimeService tradetimeService;
    @Resource
    private TradepredictinfoMapper tradepredictinfoMapper;
    @Resource
    private TradeorderinfoService tradeorderinfoService;

    public void saveTradegoodsell(TradegoodsellVo tradegoodsellVo) throws HzzBizException {
        if(!saveOrUpdate(tradegoodsellVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    public void sell(long cbiid,long gbid,int num,double price, int type) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        //if (gi.getGbiPrice().doubleValue()!=price){
        //    //TODO 价格不正确
        //    price=gi.getGbiPrice().doubleValue();
        //}
        price = gi.getGbiPrice();
        TradeconfigVo tc = tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_SELL);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal feeB=priceB.multiply(new BigDecimal(tc.getTcRate()));
        lockStock(cbiid,gbid,num,price,feeB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), type);

        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        if(tradetimeService.isInTradeTime(nowTime)){
            dealSold(cbiid,gbid,num,price);
        }
    }

    @Override
    public List<TradegoodsellVo> getSellTrade(long userId, int pageSize, int pageNum) throws HzzBizException {

        List<TradegoodsellVo> list = lambdaQuery().eq(TradegoodsellVo::getTgsSellerid, userId)
                .orderByDesc(TradegoodsellVo::getTgsCreatetime)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        return list;

    }

    @Override
    public int getSellVolume(long gbid) {

        int sellVolume = lambdaQuery().eq(TradegoodsellVo::getGbiId, gbid).eq(TradegoodsellVo::getTgsStatus, 0).count();
        return sellVolume;
    }


    public void dealSold(long cbiid,long gbid,int num,double price) throws HzzBizException {
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.eq("gbi_id",gbid);
        wrapper_c.eq("tpi_status",1);
        wrapper_c.ge("tpi_price",price);
        wrapper_c.ne("tpi_buyerid",cbiid);
        wrapper_c.orderByAsc("tpi_createtime");
        wrapper_c.last("for update");
        List<TradepredictinfoVo> list = tradepredictinfoMapper.selectList(wrapper_c);
        if(list==null||list.size()==0){
            return;
        }
        for(TradepredictinfoVo tp:list){
            try {
                tradeorderinfoService.dealSold(tp,null);
            } catch(Exception e) {
                log.error("卖出商品交易异常 cbiid={}", cbiid, e);
            }
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void lockStock(long cbiid,long gbid,int num,double price,double feeB, int type) throws HzzBizException {
        Map<String,Object> map=new HashMap<>();
        map.put("cbi_id",cbiid);
        map.put("gbi_id",gbid);
        map.put("cgr_isown",1);
        map.put("cgr_islock",0);
        map.put("cgr_ispickup",0);
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.allEq(map);
        //wrapper_c.lt("cgr_buytime", LocalDate.now());
        wrapper_c.last("limit " + num + " for update");
        List<CustomergoodsrelatedVo> cgrlist = customergoodsrelatedMapper.selectList(wrapper_c);
        if(cgrlist==null||cgrlist.size()<num){
            throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
        }
        HashMap<String, LocalDateTime> timeMap = tradetimeService.getRealTradeStartTime();
        for(CustomergoodsrelatedVo cgr:cgrlist){
            cgr.setCgrIslock(2);
            customergoodsrelatedService.saveCustomergoodsrelated(cgr);
            TradegoodsellVo tgs=new TradegoodsellVo();
            tgs.setTgsId(RandomGenUtils.genFlowNo("TGS"));
            tgs.setGiiId(cgr.getGiiId());
            tgs.setGbiId(cgr.getGbiId());
            tgs.setTgsSellerid(cgr.getCbiId());
            tgs.setTgsPrice(price);
            tgs.setTgsStatus(0);
            tgs.setTgsSaleable(1);
            tgs.setTgsCreatetime(LocalDateTime.now());
            tgs.setTgsPublishtime(timeMap.get("startTime"));
            tgs.setTgsUpdatetime(LocalDateTime.now());
            tgs.setTgsFinshitime(timeMap.get("finishTime"));
            tgs.setTgsType(1);
            tgs.setTgsOwntype(2);
            tgs.setTgsServicefee(feeB);
            tgs.setTgsTradetime(LocalDateTime.now());
            tgs.setTgsSelltype(type);
            saveTradeorderinfo(tgs);

        }
    }
    public void saveTradeorderinfo(TradegoodsellVo tradegoodsellVo) throws HzzBizException {
        if(!saveOrUpdate(tradegoodsellVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void cancel(String orderId) throws HzzBizException {
        TradegoodsellVo tgs = lambdaQuery().eq(TradegoodsellVo::getTgsId, orderId).one();
        if(tgs==null){
            throw new HzzBizException(HzzExceptionEnum.ORDER_NOTEXIST_ERROR);
        }
        if(tgs.getTgsStatus()!=0){
            //不满足取消状态
            return;
        }
        tgs.setTgsStatus(3);
        //tgs.setTgsFinshitime(LocalDateTime.now());
        tgs.setTgsUpdatetime(LocalDateTime.now());
        saveTradegoodsell(tgs);
        Map<String,Object> map=new HashMap<>();
        map.put("cbi_id",tgs.getTgsSellerid());
        map.put("gbi_id",tgs.getGbiId());
        map.put("gii_id",tgs.getGiiId());
        map.put("cgr_isown",1);
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.allEq(map);

        CustomergoodsrelatedVo cgr = customergoodsrelatedService.getOne(wrapper_c);
        cgr.setCgrIslock(0);
        customergoodsrelatedService.saveCustomergoodsrelated(cgr);
    }

    @Override
    public int hadSystemSell(long gbid) {

        int num = lambdaQuery().eq(TradegoodsellVo::getGbiId, gbid).eq(TradegoodsellVo::getTgsStatus, 0)
                .eq(TradegoodsellVo::getTgsSaleable, 1).eq(TradegoodsellVo::getTgsOwntype, 1)
                .in(TradegoodsellVo::getTgsType, 1, 2).count();
        if (num > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<TradegoodsellVo> getSellDetail(long gbiid, int pageSize, int pageNum) throws HzzBizException {

        List<TradegoodsellVo> list = lambdaQuery().eq(TradegoodsellVo::getGiiId, gbiid)
                .orderByDesc(TradegoodsellVo::getTgsCreatetime)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        return list;
    }

    @Override
    public void tradeTask(Date beginTime, Date endTime) {

        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        try {
            if(!tradetimeService.isInTradeTime(nowTime)){
                log.info("不在交易时间内，不触发定时");
                return;
            }
            List<TradegoodsellVo> list = lambdaQuery().eq(TradegoodsellVo::getTgsStatus, 0)
                    .eq(TradegoodsellVo::getTgsSaleable, 1)
                    //.in(TradegoodsellVo::getTgsSelltype, 1, 2) 1.委托卖出2正常卖出
                    .between(TradegoodsellVo::getTgsCreatetime, beginTime, endTime)
                    .orderByAsc(TradegoodsellVo::getTgsCreatetime)
                    .last("limit 1 ").list();

            if (CollectionUtils.isEmpty(list)) {
                log.info("没有卖出挂单，不触发定时");
                return;
            }
            TradegoodsellVo vo = list.get(0);
            sell(vo.getTgsSellerid(), vo.getGbiId(), 1, vo.getTgsPrice(), vo.getTgsSelltype());
        } catch (HzzBizException e) {
            log.error("交易撮合定时任务异常 msg={}", e.getErrorMsg(), e);
        }

    }

}
