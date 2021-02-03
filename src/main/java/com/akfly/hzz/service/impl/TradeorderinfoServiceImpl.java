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
    public void nomalBuy(long cbiid,long gbid,int num,double price) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        if (gi.getGbiPrice().doubleValue()!=price){
            //TODO 价格不正确
            price=gi.getGbiPrice().doubleValue();
        }
        TradeconfigVo tc = tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_BUY);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal feeB=priceB.multiply(new BigDecimal(tc.getTcRate()));
        BigDecimal totalB=priceB.multiply(BigDecimal.valueOf(num)).add(feeB.multiply(BigDecimal.valueOf(num)));
        //冻账
        frozenAccount(cbiid,totalB.doubleValue());
        //创建一条预购记录
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
        tp.setTpiSucessnum(0);
        tp.setTpiStatus(TradepredictinfoVo.STATUS_ENTRUST);

        //判断是否在交易时间
        TradetimeVo tt = tradetimeService.getTradeTime();
        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        if(DateUtil.isEffectiveTime(nowTime,tt.getTtTimeAmStart(),tt.getTtTimeAmEnd())||DateUtil.isEffectiveTime(nowTime,tt.getTtTimePmStart(),tt.getTtTimePmEnd())){
            dealSold(tp,tc);
        }else{
            tradepredictinfoService.saveTradepredictinfoVo(tp);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void frozenAccount(long cbiid,Double total) throws HzzBizException {
        CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(cbiid);
        Double balance=customerbaseinfoVo.getCbiBalance();
        Double fronze=customerbaseinfoVo.getCbiFrozen();
        BigDecimal balanceB=BigDecimal.valueOf(balance!=null?balance:0);
        BigDecimal fronzeB=BigDecimal.valueOf(fronze!=null?fronze:0);
        BigDecimal totalB=BigDecimal.valueOf(total);
        balanceB.subtract(totalB);
        fronzeB.add(totalB);
        if(balanceB.compareTo(new BigDecimal("0.0"))<0){
            throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void dealSold(TradepredictinfoVo tp,TradeconfigVo tc) throws HzzBizException {
        int need=tp.getTpiNum()-tp.getTpiSucessnum();
        QueryWrapper<TradegoodsellVo> wrapper = new QueryWrapper<TradegoodsellVo>();
        wrapper.eq("gbi_id",tp.getGbiId());
        wrapper.eq("tgs_status","0");
        wrapper.eq("tgs_saleable","1");
        wrapper.le("tgs_price",tp.getTpiPrice());
        wrapper.orderByAsc("tgs_price");
        wrapper.orderByAsc("tgs_owntype");//价格相同，系统用户在前
        wrapper.orderByAsc("tgs_tradetime");
        wrapper.last("limit "+need+" for update");
        List<TradegoodsellVo> list = tradegoodsellMapper.selectList(wrapper);
        if(list==null||list.size()==0){
            return;
        }
        BigDecimal goodsprice=new BigDecimal("0");
        BigDecimal feeprice=new BigDecimal("0");
        for(TradegoodsellVo tg:list){
            {
                //更新卖单状态
                tg.setTgsStatus(TradegoodsellVo.STATUS_SUCCESS);
                tg.setTgsTradetime(LocalDateTime.now());
                tradegoodsellService.saveTradegoodsell(tg);
            }
            {
                //生成买单
                BigDecimal price=new BigDecimal(tg.getTgsPrice());
                BigDecimal fee=price.multiply(new BigDecimal(tc.getTcRate()));
                goodsprice=goodsprice.add(price);
                feeprice=feeprice.add(fee);
                TradeorderinfoVo toi=new TradeorderinfoVo();
                toi.setToiOrderid(RandomGenUtils.genFlowNo("TOI"));
                toi.setTgsId(tg.getTgsId());
                toi.setGiiId(tg.getGiiId());
                toi.setGbiId(tg.getGbiId());
                toi.setTgsBuyerid(tp.getTpiBuyerid());
                toi.setToiSellerid(tg.getTgsSellerid());
                toi.setToiPrice(tg.getTgsPrice());
                toi.setToiStatus(TradeorderinfoVo.STATUS_SUCCESS);
                toi.setToiTradetime(LocalDateTime.now());
                toi.setToiUpdatetime(LocalDateTime.now());
                toi.setToiBuyservicefee(fee.doubleValue());
                toi.setToiSellservicefee(tg.getTgsServicefee());
                toi.setToiType(TradeorderinfoVo.TYPE_NOMAL);
                saveTradeorderinfo(toi);
            }
            {
                //卖家上账
                CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(tg.getTgsSellerid());
                Double balance=customerbaseinfoVo.getCbiBalance();
                Double total=customerbaseinfoVo.getCbiTotal();
                BigDecimal balanceB=BigDecimal.valueOf(balance!=null?balance:0);
                BigDecimal totalB=BigDecimal.valueOf(total!=null?total:0);
                balanceB.add(BigDecimal.valueOf(tg.getTgsPrice())).subtract(BigDecimal.valueOf(tg.getTgsServicefee()));
                totalB.add(BigDecimal.valueOf(tg.getTgsPrice())).subtract(BigDecimal.valueOf(tg.getTgsServicefee()));
                customerbaseinfoVo.setCbiTotal(totalB.doubleValue());
                customerbaseinfoVo.setCbiBalance(balanceB.doubleValue());
                customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
            }

        }
        {
            //更新预购信息
            if(list.size()<need){
                tp.setTpiSucessnum(tp.getTpiSucessnum()+list.size());
                tp.setTpiStatus(TradepredictinfoVo.STATUS_PARTIAL_SUCCESS);
            }else{
                tp.setTpiSucessnum(tp.getTpiSucessnum()+need);
                tp.setTpiStatus(TradepredictinfoVo.STATUS_SUCCESS);
            }
            tp.setTpiUpdatetime(LocalDateTime.now());
            tradepredictinfoService.saveTradepredictinfoVo(tp);
        }
        {
            //买家解冻记账
            CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(tp.getTpiBuyerid());
            Double balance=customerbaseinfoVo.getCbiBalance();
            Double fronze=customerbaseinfoVo.getCbiFrozen();
            Double total=customerbaseinfoVo.getCbiTotal();
            BigDecimal balanceB=BigDecimal.valueOf(balance!=null?balance:0);
            BigDecimal fronzeB=BigDecimal.valueOf(fronze!=null?fronze:0);
            BigDecimal totalB=BigDecimal.valueOf(total!=null?total:0);
            int leftnum=list.size();
            BigDecimal priceB=BigDecimal.valueOf(tp.getTpiPrice()).multiply(BigDecimal.valueOf(leftnum));
            BigDecimal feeB=BigDecimal.valueOf(tp.getTpiServicefee()).multiply(BigDecimal.valueOf(leftnum));
            balanceB.add(feeB).add(priceB);
            fronzeB.subtract(feeB).subtract(priceB);
            totalB.subtract(goodsprice).subtract(feeprice);
            balanceB.subtract(goodsprice).subtract(feeprice);
            if(balanceB.compareTo(new BigDecimal("0.0"))<0){
                throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
            }
            customerbaseinfoVo.setCbiTotal(totalB.doubleValue());
            customerbaseinfoVo.setCbiFrozen(fronzeB.doubleValue());
            customerbaseinfoVo.setCbiBalance(balanceB.doubleValue());
            customerbaseinfoService.updateUserInfo(customerbaseinfoVo);
        }

    }
    public void saveTradeorderinfo(TradeorderinfoVo tradeorderinfoVo) throws HzzBizException {
        if(!saveOrUpdate(tradeorderinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

}
