package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CbrClassEnum;
import com.akfly.hzz.constant.InOrOutTypeEnum;
import com.akfly.hzz.dto.HistoryTradeDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.akfly.hzz.mapper.TradeorderinfoMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
@Slf4j
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
    private TaskstatisticsService taskstatisticsService;

    @Resource
    private ReporttradedateService reporttradedateService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

    @Resource
    private GoodstaskinfoService goodstaskinfoService;

    @Resource
    private CustomerpickupinfoService customerpickupinfoService;

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
    public void nomalBuy(CustomerbaseinfoVo userInfo, long gbid,int num,double price,boolean isOnSale,int type) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        //if (gi.getGbiPrice() != price){
        //    //TODO 价格不正确
        //    price = gi.getGbiPrice();
        //}
        long cbiid = userInfo.getCbiId();
        if (userInfo.getCbiIsnew() == 0 && gi.getGbiType() == 2) {
            throw new HzzBizException(HzzExceptionEnum.CANNOT_BUY_NEWMAN_ERROR);
        }
        HashMap<String, LocalDateTime> timeMap = tradetimeService.getRealTradeStartTime();
        if (isOnSale) {
            price = gi.getGbiDiscountprice();
            TaskstatisticsVo task = taskstatisticsService.getTaskInfo(cbiid, gbid);
            if (task == null) throw new HzzBizException(HzzExceptionEnum.SPECIAL_BUY_MORE_ERROR);
            GoodstaskinfoVo goodstaskinfoVo = goodstaskinfoService.getGoodstaskinfoVo(gbid);
            int buyLeft = task.getBuyNum() - task.getUsedBuyNum() - (num/goodstaskinfoVo.getGtiDiscountnum()) * goodstaskinfoVo.getGtiBuynum();
            int pickUpLeft = task.getPickupNum() - task.getUsedPickupNum() - (num/goodstaskinfoVo.getGtiDiscountnum()) * goodstaskinfoVo.getGtiPickupnum();
            if (buyLeft < 0 || pickUpLeft < 0) {
                throw new HzzBizException(HzzExceptionEnum.SPECIAL_BUY_MORE_ERROR);
            }
        } else {
            price = gi.getGbiPrice();
        }
        TradeconfigVo tc = tradeconfigService.getTradeconfig(TradeconfigVo.TCTYPE_BUY);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal totalFeeInit = priceB.multiply(BigDecimal.valueOf(tc.getTcRate())).multiply(BigDecimal.valueOf(num));
        log.info("初始化服务费 totalFeeInit={}", totalFeeInit);
        BigDecimal totalB = priceB.multiply(BigDecimal.valueOf(num)).add(totalFeeInit);
        //冻账
        customerbaseinfoService.frozenAccount(cbiid,totalB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        //创建一条预购记录
        TradepredictinfoVo tp=new TradepredictinfoVo();
        tp.setTpiId(RandomGenUtils.genFlowNo("TPI"));
        tp.setGbiId(gbid);
        tp.setTpiBuyerid(cbiid);
        tp.setTpiPrice(price);
        tp.setTpiNum(num);
        tp.setTpiStatus(0);
        tp.setTpiCreatetime(LocalDateTime.now());
        tp.setTpiBuytime(timeMap.get("startTime"));
        tp.setTpiFinishtime(timeMap.get("finishTime"));
        tp.setTpiTradetime(LocalDateTime.now());
        tp.setTpiUpdatetime(LocalDateTime.now());
        tp.setTpiServicefee(totalFeeInit.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        tp.setTpiSucessnum(0);
        if(isOnSale){
            tp.setTpiGoodstype(3);
        } else {
            tp.setTpiGoodstype(gi.getGbiType());
        }

        tp.setTpiStatus(TradepredictinfoVo.STATUS_ENTRUST);
        //判断是否在交易时间
        String nowTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        if(tradetimeService.isInTradeTime(nowTime)){
            tp.setTpiType(type);
            //tradepredictinfoService.saveTradepredictinfoVo(tp);
            dealSold(tp,tc,isOnSale);
        }else{
            if(type!=TradepredictinfoVo.TYPE_ENTRUST){
                throw new HzzBizException(HzzExceptionEnum.TRADE_TIME_ERROR);
            }
            tp.setTpiType(TradepredictinfoVo.TYPE_ENTRUST);
            checkBuyNum(gi.getGbiLimitperson(), tp);
            tradepredictinfoService.saveTradepredictinfoVo(tp);
        }
        if(isOnSale){
            //特价品
            boolean release=tradepredictinfoService.releaseOne(tp.getTpiId(),5);
            if(release){
                //释放成功说明没有买入成功
                throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
            } else {
                taskstatisticsService.saveOrUpdateForNoSpecial(cbiid, gbid, num);
                // 特价商品购买成功后，更新用户为非新手用户
                if (userInfo.getCbiIsnew() == 1) {
                    userInfo.setCbiIsnew(0);
                    customerbaseinfoService.updateUserInfo(userInfo);
                }
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

        // 校验当天购买量不能超出商品基本信息上面的当日限制购买量
        checkBuyNum(gi.getGbiLimitperson(), tp);

        int need = tp.getTpiNum() - tp.getTpiSucessnum();
        QueryWrapper<TradegoodsellVo> wrapper = new QueryWrapper<TradegoodsellVo>();
        wrapper.eq("gbi_id",tp.getGbiId());
        wrapper.eq("tgs_status","0");
        wrapper.eq("tgs_saleable","1");
        wrapper.ne("tgs_sellerid",tp.getTpiBuyerid());
        wrapper.le("tgs_price",tp.getTpiPrice());
        if(isOnSale){
            wrapper.eq("tgs_type",3);
        }else{
            //wrapper.eq("tgs_type",1);
            wrapper.in("tgs_type", Arrays.asList(1, 2));
        }
        wrapper.orderByAsc("tgs_price");
        wrapper.orderByAsc("tgs_owntype");//价格相同，系统用户在前
        wrapper.orderByAsc("tgs_tradetime");
        wrapper.last("limit " + need + " for update");
        List<TradegoodsellVo> list = tradegoodsellMapper.selectList(wrapper);
        log.info("tpiId={}, tpiNum={}, successNum={}, need={}", tp.getTpiId(), tp.getTpiNum(), tp.getTpiSucessnum(), need);
        if(isOnSale){
            if(list==null||list.size()<need){
                throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
            }
        }
        {
            int successNum = 0;
            //更新预购信息
            if(list.size()<need){
                successNum = tp.getTpiSucessnum() + list.size();
                // tp.setTpiType(TradepredictinfoVo.TYPE_ENTRUST);
                //tp.setTpiStatus(TradepredictinfoVo.STATUS_PARTIAL_SUCCESS);
            } else {
                successNum = tp.getTpiSucessnum() + need;
                tp.setTpiStatus(TradepredictinfoVo.STATUS_SUCCESS);
            }
            log.info("本次匹配成功数量 successNum={}", successNum);
            if (successNum > 0) {
                BigDecimal successNumB = BigDecimal.valueOf(successNum);
                BigDecimal totalFeeSuccess = BigDecimal.valueOf(tp.getTpiPrice()).multiply(BigDecimal.valueOf(tc.getTcRate())).multiply(successNumB);
                tp.setTpiServicefee(totalFeeSuccess.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                tp.setTpiSucessnum(successNum);
            }
            tp.setTpiUpdatetime(LocalDateTime.now());
            tradepredictinfoService.saveTradepredictinfoVo(tp);
        }
        if(list.size()==0){
            return;
        }
        BigDecimal goodsprice=new BigDecimal("0");
        BigDecimal feeprice=new BigDecimal("0");
        for(TradegoodsellVo tg : list){
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
                if (cgr == null) throw new HzzBizException(HzzExceptionEnum.WULIAO_CANNOT_BUY_ERROR);
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
                //cgrnew.setCgrSelltime(LocalDateTime.now());
                cgrnew.setCgrUpdatetime(LocalDateTime.now());
                cgrnew.setCgrForzentime(LocalDateTime.now().plusDays(Long.parseLong(gi.getGbiFrozendays()!=null?gi.getGbiFrozendays().toString():"0")));
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
                toi.setToiBuyservicefee(fee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                toi.setToiSellservicefee(tg.getTgsServicefee());
                if (isOnSale) {
                    toi.setToiType(3);
                } else {
                    toi.setToiType(tp.getTpiGoodstype());
                }
                toi.setTpiId(tp.getTpiId());
                saveTradeorderinfo(toi);

                //卖家上账
                CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(tg.getTgsSellerid());
                Double balance=customerbaseinfoVo.getCbiBalance();
                Double total=customerbaseinfoVo.getCbiTotal();
                BigDecimal balanceB = BigDecimal.valueOf(balance!=null?balance:0);
                BigDecimal totalB = BigDecimal.valueOf(total!=null?total:0);
                BigDecimal goodsPrice = BigDecimal.valueOf(tg.getTgsPrice());
                BigDecimal serviceFee = BigDecimal.valueOf(tg.getTgsServicefee());
                BigDecimal cost = goodsPrice.subtract(serviceFee);
                balanceB = balanceB.add(cost);
                totalB = totalB.add(cost);
                customerbaseinfoVo.setCbiTotal(totalB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                customerbaseinfoVo.setCbiBalance(balanceB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                customerbaseinfoService.updateUserInfo(customerbaseinfoVo);

                // 记录卖出商品和卖出服务费的资金流水,一个成交单一笔记录
                customerbillrelatedService.saveBills(tg.getTgsSellerid(), toi.getToiOrderid(),
                        goodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), InOrOutTypeEnum.IN, CbrClassEnum.GOODSIN);
                customerbillrelatedService.saveBills(tg.getTgsSellerid(), toi.getToiOrderid(),
                        serviceFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), InOrOutTypeEnum.OUT, CbrClassEnum.SELLFEE);
            }


        }
        {
            //更新交易数据
            LocalDateTime now=LocalDateTime.now();
            ReporttradedateVo rt=new ReporttradedateVo();
            rt.setRtiYear(Integer.parseInt(now.format(DateTimeFormatter.ofPattern("yyyy"))));
            rt.setRtiMonth(Integer.parseInt(now.format(DateTimeFormatter.ofPattern("yyyyMM"))));
            rt.setRtiWeek(Integer.parseInt(now.format(DateTimeFormatter.ofPattern("yyyy"))+String.format("%2d", now.get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfYear())).replace(" ", "0")));
            rt.setRtiDate(Integer.parseInt(now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
            rt.setRtiHour(now.getHour());
            rt.setRtiGbid(tp.getGbiId());
            GoodsbaseinfoVo gbi = goodsbaseinfoService.getGoodsbaseinfoVo(tp.getGbiId());
            rt.setRitGbiname(gbi.getGbiName());
            rt.setRtiNum(list.size());
            rt.setRtiMoney(goodsprice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            rt.setRtiCreatetime(LocalDateTime.now());
            rt.setRtiUpdatetime(LocalDateTime.now());
            log.info("数据统计={}", JsonUtils.toJson(rt));
            reporttradedateService.saveReporttradedateVo(rt);
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
            BigDecimal feeB=BigDecimal.valueOf(tp.getTpiServicefee());
            // 解冻
            balanceB=balanceB.add(feeB).add(priceB);
            fronzeB=fronzeB.subtract(feeB).subtract(priceB);
            // 扣款，并校验防止解冻金额不够扣除
            totalB=totalB.subtract(goodsprice).subtract(feeprice);
            balanceB=balanceB.subtract(goodsprice).subtract(feeprice);
            if(balanceB.compareTo(new BigDecimal("0.0"))<0){
                throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
            }
            customerbaseinfoVo.setCbiTotal(totalB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            customerbaseinfoVo.setCbiFrozen(fronzeB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            customerbaseinfoVo.setCbiBalance(balanceB.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            customerbaseinfoVo.setCbiUpdatetime(LocalDateTime.now());
            customerbaseinfoService.updateUserInfo(customerbaseinfoVo);


            // 记录买入商品和买入服务费的资金流水，一个预购单对应多个资金流水（可能多次成交）
            customerbillrelatedService.saveBills(tp.getTpiBuyerid(), tp.getTpiId(),
                    goodsprice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), InOrOutTypeEnum.OUT, CbrClassEnum.GOOGSOUT);
            customerbillrelatedService.saveBills(tp.getTpiBuyerid(), tp.getTpiId(),
                    feeprice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), InOrOutTypeEnum.OUT, CbrClassEnum.BUYFEE);
        }

    }

    @Override
    public List<TradeorderinfoVo> getBuyNoSpecialTrade(long userid) {

        QueryWrapper wrapper_c = new QueryWrapper();
        wrapper_c.select("tgs_buyerid,gbi_id,count(id) as stock");
        wrapper_c.eq("tgs_buyerid", userid);
        wrapper_c.eq("toi_status", 3);
        wrapper_c.in("toi_type", Arrays.asList(1, 2));
        wrapper_c.ge("toi_tradetime", LocalDate.now());
        wrapper_c.groupBy("gbi_id");

        List<TradeorderinfoVo> list = getBaseMapper().selectList(wrapper_c);
        return list;

    }

    @Override
    public int getActiveUser(List<Long> userIds) {

        int count = lambdaQuery().in(TradeorderinfoVo::getTgsBuyerid, userIds).or().eq(TradeorderinfoVo::getToiSellerid, userIds)
                .ge(TradeorderinfoVo::getToiTradetime, LocalDate.now()).count();
        return count;
    }

    @Override
    public BigDecimal getAmount(List<Long> userIds, int flag, int direction) {

        QueryWrapper<TradeorderinfoVo> contract_wrapper = new QueryWrapper<TradeorderinfoVo>();

        if (direction == 0) {
            if (flag == 0) {
                contract_wrapper.in("tgs_buyerid", userIds);
                contract_wrapper.in("toi_type", 1, 2);
            } else {
                contract_wrapper.in("tgs_buyerid", userIds);
                contract_wrapper.eq("toi_type", 3);
            }
        } else {
            contract_wrapper.in("toi_sellerid", userIds);
        }
        contract_wrapper.select("ifnull(sum(toi_price),0) as total_money ");
        Map<String, Object> map = getMap(contract_wrapper);
        BigDecimal total_amount = new BigDecimal(String.valueOf(map.get("total_money"))).setScale(2, BigDecimal.ROUND_HALF_UP);
        return total_amount;
    }

    @Override
    public HistoryTradeDto getSumAmount(Long userId, boolean isCurrentDay) {

        HistoryTradeDto dto = new HistoryTradeDto();
        dto.setBuyNum(getAmount(userId, 0, 0, isCurrentDay));
        dto.setSellNum(getAmount(userId, 0, 1, isCurrentDay));
        dto.setPickupNum(customerpickupinfoService.getPickUpAmountOne(userId, isCurrentDay));
        dto.setSpecialBuyNum(getAmount(userId, 1, 0, isCurrentDay));
        return dto;
    }

    public void saveTradeorderinfo(TradeorderinfoVo tradeorderinfoVo) throws HzzBizException {
        if(!saveOrUpdate(tradeorderinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }


    public void checkBuyNum(Integer gbiLimitPerson, TradepredictinfoVo tp) throws HzzBizException {

        if (gbiLimitPerson == null ) return; // 没有配置的话，不限制个人当天购买数量
        int need = tp.getTpiNum() - tp.getTpiSucessnum();
        //{  校验所有该产品的购买量
        //    QueryWrapper wrapper_c=new QueryWrapper();
        //    wrapper_c.eq("cbi_id",tp.getTpiBuyerid());
        //    wrapper_c.eq("gbi_id",tp.getGbiId());
        //    wrapper_c.eq("cgr_isown",1);
        //    List list = customergoodsrelatedService.list(wrapper_c);
        //    int gbiLimitPerson = gi.getGbiLimitperson() == null ? 0 : gi.getGbiLimitperson();
        //    if(gbiLimitPerson < (list.size() + need)){
        //        throw new HzzBizException(HzzExceptionEnum.LIMIT_PERSON_ERROR);
        //    }
        //}

        // 校验用户该产品当天的购买量
        Date now = new Date();
        Date beginTime = DateUtil.getDateBegin(now);
        int buyCount = lambdaQuery().eq(TradeorderinfoVo::getGbiId, tp.getGbiId()).eq(TradeorderinfoVo::getTgsBuyerid, tp.getTpiBuyerid())
                .between(TradeorderinfoVo::getToiTradetime, beginTime, now).count();
        if(gbiLimitPerson < (buyCount + need)){
            throw new HzzBizException(HzzExceptionEnum.LIMIT_PERSON_ERROR);
        }

    }

    private BigDecimal getAmount(long userId, int flag, int direction, boolean isCurrentDay) {

        QueryWrapper<TradeorderinfoVo> wrapper = new QueryWrapper<TradeorderinfoVo>();
        if (isCurrentDay) {
            wrapper.ge("toi_tradetime", LocalDate.now());
        } else {
            wrapper.lt("toi_tradetime", LocalDate.now());
        }
        buildQueryWrapper(userId, flag, direction, wrapper);
        wrapper.select("ifnull(sum(toi_price),0) as total_money ");
        Map<String, Object> map = getMap(wrapper);
        BigDecimal total_amount = new BigDecimal(String.valueOf(map.get("total_money"))).setScale(2, BigDecimal.ROUND_HALF_UP);
        return total_amount;
    }

    private void buildQueryWrapper(long userId, int flag, int direction,  QueryWrapper<TradeorderinfoVo> wrapper) {

        if (direction == 0) {
            if (flag == 0) {
                wrapper.eq("tgs_buyerid", userId);
                wrapper.in("toi_type", 1, 2);
            } else {
                wrapper.in("tgs_buyerid", userId);
                wrapper.eq("toi_type", 3);
            }
        } else {
            wrapper.in("toi_sellerid", userId);
        }

    }

}
