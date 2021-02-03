package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomerbaseinfoMapper;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.TradegoodsellMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.*;
import com.akfly.hzz.mapper.TradeorderinfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
        CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoMapper.selectByIdForUpdate(cbiid);
        Double balance=customerbaseinfoVo.getCbiBalance();
        Double fronze=customerbaseinfoVo.getCbiFrozen();
        Double total=customerbaseinfoVo.getCbiTotal();
        BigDecimal balanceB=new BigDecimal(balance!=null?balance:new Double(0.0));
        BigDecimal fronzeB=new BigDecimal(fronze!=null?fronze:new Double(0.0));
        BigDecimal totalB=new BigDecimal(fronze!=null?total:new Double(0.0));
        BigDecimal amountB=new BigDecimal(price);
        amountB=amountB.multiply(new BigDecimal(num));
        amountB=amountB.multiply(new BigDecimal(tc.getTcRate())).add(amountB);
        if(balanceB.compareTo(amountB)<0){
            throw new HzzBizException(HzzExceptionEnum.ACCOUNT_BALACE_ERROR);
        }

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
        //TODO 这里判断交易时间在不在范围
        QueryWrapper<TradegoodsellVo> wrapper = new QueryWrapper<TradegoodsellVo>();
        wrapper.eq("gbi_id",gbid);
        wrapper.eq("tgs_status","0");
        wrapper.eq("tgs_saleable","1");
        wrapper.le("tgs_price",price);
        wrapper.orderByAsc("tgs_price");
        wrapper.orderByAsc("tgs_owntype");//价格相同，系统用户在前
        wrapper.orderByAsc("tgs_tradetime");
        wrapper.last("limit "+num+" for update");
        List<TradegoodsellVo> list = tradegoodsellMapper.selectList(wrapper);
        List<TradeorderinfoVo> buylist=new ArrayList<>();
        Map<String,BigDecimal> map=dealSold(list,tc,buylist);
        BigDecimal totalprice = map.get("totalprice");
        BigDecimal feeprice = map.get("feeprice");

        int success=num;
        if(list.size()<num){
            //部分可买,还需要再插入一些数据
            for(int i=0;i<num-list.size();i++){
                TradeorderinfoVo toi=new TradeorderinfoVo();
                //TODO 设置toi
                buylist.add(toi);
            }
            success=list.size();
        }
        TradepredictinfoVo tp=new TradepredictinfoVo();
        tp.setGbiId(gbid);
        //TODO 设置tp参数
        tradepredictinfoService.saveTradepredictinfoVo(tp);
        saveBatch(buylist);
        //TODO 买家上账
    }
    private Map<String,BigDecimal> dealSold(List<TradegoodsellVo> list,TradeconfigVo tc,List<TradeorderinfoVo> toilist){
        BigDecimal goodsprice=new BigDecimal("0");
        BigDecimal feeprice=new BigDecimal("0");
        for(TradegoodsellVo tg:list){
            tg.setTgsStatus(TradegoodsellVo.STATUS_SUCCESS);
            //TODO 设置tg参数
            BigDecimal price=new BigDecimal(tg.getTgsPrice());
            BigDecimal fee=price.multiply(new BigDecimal(tc.getTcRate()));
            goodsprice=goodsprice.add(price);
            feeprice=feeprice.add(fee);
            TradeorderinfoVo toi=new TradeorderinfoVo();
            //TODO 设置toi
            toilist.add(toi);
            //TODO 更新卖数据tg
            //TODO 卖家上账
        }
        Map<String,BigDecimal> map=new HashMap<>();
        map.put("goodsprice",goodsprice);
        map.put("feeprice",feeprice);
        map.put("totalprice",goodsprice.add(feeprice));
        return map;
    }
}
