package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.akfly.hzz.mapper.TradeorderinfoMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
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

    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;
    @Resource
    private CustomergoodsrelatedMapper customergoodsrelatedMapper;

    @Resource
    private ReporttradedateService reporttradedateService;
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
    public void nomalBuy(long cbiid,long gbid,int num,double price,boolean isOnSale,int type) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        //if (gi.getGbiPrice() != price){
        //    //TODO 价格不正确
        //    price = gi.getGbiPrice();
        //}
        price = gi.getGbiPrice();
        TradeconfigVo tc = tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_BUY);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal feeB=priceB.multiply(new BigDecimal(tc.getTcRate()));
        BigDecimal totalB=priceB.multiply(BigDecimal.valueOf(num)).add(feeB.multiply(BigDecimal.valueOf(num)));
        //冻账
        customerbaseinfoService.frozenAccount(cbiid,totalB.doubleValue());
        //创建一条预购记录
        TradepredictinfoVo tp=new TradepredictinfoVo();
        tp.setTpiId(RandomGenUtils.genFlowNo("TPI"));
        tp.setGbiId(gbid);
        tp.setTpiBuyerid(cbiid);
        tp.setTpiPrice(price);
        tp.setTpiNum(num);
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
        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        if(tradetimeService.isInTradeTime(nowTime)){
            tp.setTpiType(type);
            //tradepredictinfoService.saveTradepredictinfoVo(tp);
            dealSold(tp,tc,isOnSale);
        }else{
            if(type!=TradepredictinfoVo.TYPE_ENTRUST){
                throw new HzzBizException(HzzExceptionEnum.TRADE_TIME_ERROR);
            }
            tp.setTpiType(TradepredictinfoVo.TYPE_ENTRUST);
            int need=tp.getTpiNum()-tp.getTpiSucessnum();
            {
                QueryWrapper wrapper_c=new QueryWrapper();
                wrapper_c.eq("cbi_id",tp.getTpiBuyerid());
                wrapper_c.eq("gbi_id",tp.getGbiId());
                wrapper_c.eq("cgr_isown",1);
                List list = customergoodsrelatedService.list(wrapper_c);
                if(gi.getGbiLimitperson()<list.size()+need){
                    throw new HzzBizException(HzzExceptionEnum.LIMIT_PERSON_ERROR);
                }
            }
            tradepredictinfoService.saveTradepredictinfoVo(tp);
        }
        if(isOnSale){
            //特价品
            boolean release=tradepredictinfoService.releaseOne(tp.getTpiId(),5);
            if(release){
                //释放成功说明没有买入成功
                throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
            }
        }
    }

    @Override
    public void dealSold(TradepredictinfoVo tp, TradeconfigVo tc) throws HzzBizException {
        dealSold(tp,tc,false);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dealSold(TradepredictinfoVo tp,TradeconfigVo tc,boolean isOnSale) throws HzzBizException {
        if(tp.getId()!=null){
            //这里是已存在而非新创建的
            tp= tradepredictinfoService.getById(tp.getId());
        }
        if(tp.getTpiStatus()!=1){
            //预下单状态已变更，无需继续处理
            return;
        }
        if(tc==null){
            tc=tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_BUY);
        }
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(tp.getGbiId());
        int need=tp.getTpiNum()-tp.getTpiSucessnum();
        {
            QueryWrapper wrapper_c=new QueryWrapper();
            wrapper_c.eq("cbi_id",tp.getTpiBuyerid());
            wrapper_c.eq("gbi_id",tp.getGbiId());
            wrapper_c.eq("cgr_isown",1);
            List list = customergoodsrelatedService.list(wrapper_c);
            if(gi.getGbiLimitperson()<list.size()+need){
                throw new HzzBizException(HzzExceptionEnum.LIMIT_PERSON_ERROR);
            }
        }
        QueryWrapper<TradegoodsellVo> wrapper = new QueryWrapper<TradegoodsellVo>();
        wrapper.eq("gbi_id",tp.getGbiId());
        wrapper.eq("tgs_status","0");
        wrapper.eq("tgs_saleable","1");
        wrapper.le("tgs_price",tp.getTpiPrice());
        if(isOnSale){
            wrapper.eq("tgs_type",3);
        }else{
            wrapper.eq("tgs_type",1);
        }
        wrapper.orderByAsc("tgs_price");
        wrapper.orderByAsc("tgs_owntype");//价格相同，系统用户在前
        wrapper.orderByAsc("tgs_tradetime");
        wrapper.last("limit "+need+" for update");
        List<TradegoodsellVo> list = tradegoodsellMapper.selectList(wrapper);
        if(list==null||list.size()==0){
            return;
        }
        if(isOnSale){
            if(list.size()<need){
                return;
            }
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
                //更新物料关系
                Map<String,Object> map=new HashMap<>();
                map.put("cbi_id",tg.getTgsSellerid());
                map.put("gii_id",tg.getGiiId());
                map.put("cgr_isown",1);
                QueryWrapper wrapper_c=new QueryWrapper();
                wrapper_c.allEq(map);
                wrapper_c.last("for update");
                CustomergoodsrelatedVo cgr = customergoodsrelatedMapper.selectOne(wrapper_c);
                cgr.setCgrIslock(0);
                cgr.setCgrSelltime(LocalDateTime.now());
                cgr.setCgrIsown(0);
                customergoodsrelatedService.saveCustomergoodsrelated(cgr);
                CustomergoodsrelatedVo cgrnew=new CustomergoodsrelatedVo();
                cgrnew.setCbiId(tp.getTpiBuyerid());
                cgrnew.setGiiId(cgr.getGiiId());
                cgrnew.setGbiId(cgr.getGbiId());
                cgrnew.setCgrIsown(1);
                cgrnew.setCgrIslock(1);
                cgrnew.setCgrIspickup(0);
                cgrnew.setCgrBuytime(LocalDateTime.now());
                cgrnew.setCgrSelltime(LocalDateTime.now());
                cgrnew.setCgrUpdatetime(LocalDateTime.now());
                customergoodsrelatedService.saveCustomergoodsrelated(cgrnew);

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
            //更新交易数据
            LocalDateTime now=LocalDateTime.now();
            ReporttradedateVo rt=new ReporttradedateVo();
            rt.setRtiYear(now.getYear());
            rt.setRtiMonth(now.getMonthValue());
            rt.setRtiWeek(now.get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfYear()));
            rt.setRtiDate(now.getDayOfMonth());
            rt.setRtiHour(now.getHour());
            rt.setRtiGbid(tp.getGbiId());
            GoodsbaseinfoVo gbi = goodsbaseinfoService.getGoodsbaseinfoVo(tp.getGbiId());
            rt.setRitGbiname(gbi.getGbiName());
            rt.setRtiNum(list.size());
            rt.setRtiMoney(goodsprice.doubleValue());
            rt.setRtiCreatetime(LocalDateTime.now());
            rt.setRtiUpdatetime(LocalDateTime.now());
            reporttradedateService.saveReporttradedateVo(rt);
        }
        {
            //更新预购信息
            if(list.size()<need){
                tp.setTpiSucessnum(tp.getTpiSucessnum()+list.size());
                //tp.setTpiStatus(TradepredictinfoVo.STATUS_PARTIAL_SUCCESS);
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

    @Override
    public List<TradeorderinfoVo> getBuyNoSpecialTrade(long userid) {

        QueryWrapper wrapper_c = new QueryWrapper();
        wrapper_c.select("tgs_buyerid,gbi_id,count(id) as stock");
        wrapper_c.eq("tgs_buyerid", userid);
        wrapper_c.eq("toi_status", 3);
        wrapper_c.in("toi_type", Arrays.asList(1, 2));
        wrapper_c.groupBy("gbi_id");

        List<TradeorderinfoVo> list = getBaseMapper().selectList(wrapper_c);
        return list;

    }

    public void saveTradeorderinfo(TradeorderinfoVo tradeorderinfoVo) throws HzzBizException {
        if(!saveOrUpdate(tradeorderinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

}
