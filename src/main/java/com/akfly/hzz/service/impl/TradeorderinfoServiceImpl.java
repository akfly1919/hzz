package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.*;
import com.akfly.hzz.mapper.TradeorderinfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 交易市场买货订单 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradeorderinfoServiceImpl extends ServiceImpl<TradeorderinfoMapper, TradeorderinfoVo> implements TradeorderinfoService {

    @Resource
    GoodsbaseinfoService goodsbaseinfoService;
    @Resource
    private CustomerbaseinfoMapper customerbaseinfoMapper;

    @Resource
    private TradegoodsellMapper tradegoodsellMapper;
    @Resource
    private TradetimeService tradetimeService;

    @Resource
    private TradeconfigService tradeconfigService;

    @Resource
    private TradepredictinfoService tradepredictinfoService;

    @Resource
    private TradegoodsellService tradegoodsellService;

    @Resource
    private  CustomerbaseinfoService customerbaseinfoService;
    @Override
    public List<TradeorderinfoVo> getTradeorderinfoVo(int pageNum, int pageSize, long cbiid, Date beginTime, Date endTime) throws HzzBizException {
        List<TradeorderinfoVo> tradeorderinfoVos = lambdaQuery()
                .eq(TradeorderinfoVo::getTgsBuyerid, cbiid).between(TradeorderinfoVo::getToiTradetime, beginTime, endTime)
                .orderByDesc(TradeorderinfoVo::getToiTradetime).last("limit " + pageNum * pageSize + "," + pageSize + " ").list();

        return tradeorderinfoVos;
    }

    @Override
    public void insertTradeOrder(TradeorderinfoVo vo) throws HzzBizException {

    }

    @Override
    public void updateTradeOrder(TradeorderinfoVo vo) throws HzzBizException {

    }
    @Transactional(rollbackFor = Exception.class)
    public void nomalBuy(long cbiid,long gbid,int num,double price) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        if (gi.getGbiPrice().doubleValue()!=price){
            //TODO 价格不正确
            price=gi.getGbiPrice().doubleValue();
        }
        TradeconfigVo tc = tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_BUY);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal feeB=priceB.multiply(new BigDecimal(tc.getTcRate()));


        /*BigDecimal amountB=new BigDecimal(price);
        amountB=amountB.multiply(new BigDecimal(num));
        amountB=amountB.multiply(new BigDecimal(tc.getTcRate())).add(amountB);
        if(balanceB.compareTo(amountB)<0){
            throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
        }*/

        /* 写串了，这是卖出逻辑
        QueryWrapper<CustomergoodsrelatedVo> wrapper = new QueryWrapper<CustomergoodsrelatedVo>();
        wrapper.eq("cbi_id",cbiid);
        wrapper.eq("gbi_id",gbid);
        wrapper.eq("cgr_islock","0");
        wrapper.eq("cgr_isown","1");
        wrapper.last("limit "+num+" for update");
        List<CustomergoodsrelatedVo> list = customergoodsrelatedMapper.selectList(wrapper);
        if(list.size()<num){
            throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
        }*/
        TradetimeVo tt = tradetimeService.getTradeTime();
        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        List<TradegoodsellVo> list=new ArrayList<>();
        if(DateUtil.isEffectiveTime(nowTime,tt.getTtTimeAmStart(),tt.getTtTimeAmEnd())||DateUtil.isEffectiveTime(nowTime,tt.getTtTimePmStart(),tt.getTtTimePmEnd())){
            QueryWrapper<TradegoodsellVo> wrapper = new QueryWrapper<TradegoodsellVo>();
            wrapper.eq("gbi_id",gbid);
            wrapper.eq("tgs_status","0");
            wrapper.eq("tgs_saleable","1");
            wrapper.le("tgs_price",price);
            wrapper.orderByAsc("tgs_price");
            wrapper.orderByAsc("tgs_owntype");//价格相同，系统用户在前
            wrapper.orderByAsc("tgs_tradetime");
            wrapper.last("limit "+num+" for update");
            list = tradegoodsellMapper.selectList(wrapper);
        }

        Map<String,BigDecimal> map=dealSold(cbiid,list,tc);

        TradepredictinfoVo tp=new TradepredictinfoVo();
        tp.setTpiId(RandomGenUtils.genFlowNo("TPI"));
        tp.setGbiId(gbid);
        tp.setTpiBuyerid(cbiid);
        tp.setTpiPrice(price);
        tp.setTpiNum(num);
        tp.setTpiType(TradepredictinfoVo.TYPE_NOMAL);
        tp.setTpiStatus(0);
        tp.setTpiCreatetime(LocalDateTime.now());
        tp.setTpiBuytime(LocalDateTime.now());
        tp.setTpiFinishtime(LocalDateTime.now());
        tp.setTpiTradetime(LocalDateTime.now());
        tp.setTpiUpdatetime(LocalDateTime.now());
        tp.setTpiServicefee(feeB.doubleValue());
        tp.setTpiSucessnum(num);
        if(list.size()<num&&list.size()>0){
            tp.setTpiSucessnum(list.size());
            tp.setTpiStatus(TradepredictinfoVo.STATUS_PARTIAL_SUCCESS);
        }else if(list.size()==0){
            tp.setTpiSucessnum(0);
            tp.setTpiStatus(TradepredictinfoVo.STATUS_ENTRUST);
        }else{
            tp.setTpiSucessnum(num);
            tp.setTpiStatus(TradepredictinfoVo.STATUS_SUCCESS);
        }
        tradepredictinfoService.saveTradepredictinfoVo(tp);
        //买家账
        CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(cbiid);
        Double balance=customerbaseinfoVo.getCbiBalance();
        Double fronze=customerbaseinfoVo.getCbiFrozen();
        Double total=customerbaseinfoVo.getCbiTotal();
        BigDecimal balanceB=new BigDecimal(balance!=null?balance:new Double(0.0));
        BigDecimal fronzeB=new BigDecimal(fronze!=null?fronze:new Double(0.0));
        BigDecimal totalB=new BigDecimal(total!=null?total:new Double(0.0));
        int leftnum=num-list.size();
        BigDecimal totalprice = map.get("totalprice");
        BigDecimal feeprice = map.get("feeprice");
        totalB.subtract(totalprice);
        balanceB.subtract(totalprice);
        priceB=priceB.multiply(new BigDecimal(leftnum));
        balanceB=balanceB.subtract(priceB);
        feeB=feeB.multiply(new BigDecimal(leftnum));
        balanceB=balanceB.subtract(feeB);
        if(balanceB.compareTo(new BigDecimal("0.0"))<0){
            throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
        }
        fronzeB=fronzeB.add(feeB).add(priceB);
        customerbaseinfoVo.setCbiTotal(totalB.doubleValue());
        customerbaseinfoVo.setCbiFrozen(fronzeB.doubleValue());
        customerbaseinfoVo.setCbiBalance(balanceB.doubleValue());
        customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
    }
    private Map<String,BigDecimal> dealSold(long cbiid,List<TradegoodsellVo> list,TradeconfigVo tc) throws HzzBizException {
        BigDecimal goodsprice=new BigDecimal("0");
        BigDecimal feeprice=new BigDecimal("0");
        for(TradegoodsellVo tg:list){
            tg.setTgsStatus(TradegoodsellVo.STATUS_SUCCESS);
            tg.setTgsTradetime(LocalDateTime.now());
            tradegoodsellService.saveTradegoodsell(tg);
            BigDecimal price=new BigDecimal(tg.getTgsPrice());
            BigDecimal fee=price.multiply(new BigDecimal(tc.getTcRate()));
            goodsprice=goodsprice.add(price);
            feeprice=feeprice.add(fee);
            TradeorderinfoVo toi=new TradeorderinfoVo();
            toi.setToiOrderid(RandomGenUtils.genFlowNo("TOI"));
            toi.setTgsId(tg.getTgsId());
            toi.setGiiId(tg.getGiiId());
            toi.setGbiId(tg.getGbiId());
            toi.setTgsBuyerid(cbiid);
            toi.setToiSellerid(tg.getTgsSellerid());
            toi.setToiPrice(tg.getTgsPrice());
            toi.setToiStatus(TradeorderinfoVo.STATUS_SUCCESS);
            toi.setToiTradetime(LocalDateTime.now());
            toi.setToiUpdatetime(LocalDateTime.now());
            toi.setToiBuyservicefee(fee.doubleValue());
            toi.setToiSellservicefee(tg.getTgsServicefee());
            toi.setToiType(TradeorderinfoVo.TYPE_NOMAL);
            saveTradeorderinfo(toi);
            CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(tg.getTgsSellerid());
            Double balance=customerbaseinfoVo.getCbiBalance();
            Double fronze=customerbaseinfoVo.getCbiFrozen();
            Double total=customerbaseinfoVo.getCbiTotal();
            BigDecimal balanceB=new BigDecimal(balance!=null?balance:new Double(0.0));
            BigDecimal fronzeB=new BigDecimal(fronze!=null?fronze:new Double(0.0));
            BigDecimal totalB=new BigDecimal(fronze!=null?total:new Double(0.0));
            balanceB.add(new BigDecimal(tg.getTgsPrice()));
            fronzeB.subtract(new BigDecimal(tg.getTgsServicefee()));
            totalB.subtract(new BigDecimal(tg.getTgsServicefee()));
            customerbaseinfoVo.setCbiFrozen(fronzeB.doubleValue());
            customerbaseinfoVo.setCbiTotal(totalB.doubleValue());
            customerbaseinfoVo.setCbiBalance(balanceB.doubleValue());
            customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
        }
        Map<String,BigDecimal> map=new HashMap<>();
        map.put("goodsprice",goodsprice);
        map.put("feeprice",feeprice);
        map.put("totalprice",goodsprice.add(feeprice));
        return map;
    }
    public void saveTradeorderinfo(TradeorderinfoVo tradeorderinfoVo) throws HzzBizException {
        if(!saveOrUpdate(tradeorderinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

}
