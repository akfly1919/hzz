package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.mapper.CustomerpickupinfoMapper;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.akfly.hzz.vo.CustomerpickupinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户提货表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class CustomerpickupinfoServiceImpl extends ServiceImpl<CustomerpickupinfoMapper, CustomerpickupinfoVo> implements CustomerpickupinfoService {

    @Resource
    private CustomergoodsrelatedMapper customergoodsrelatedMapper;
    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;
    @Resource
    private CustomeraddressinfoService customeraddressinfoService;
    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private CustomerpickupdetailService customerpickupdetailService;

    @Resource
    private CustomerpickupinfoMapper customerpickupinfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void pickup(long cbiid,long gbid,int num,long caiid) throws HzzBizException {

        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);

        if(gi.getGbiLimitpickup()!=null&&num<gi.getGbiLimitpickup()){
            throw new HzzBizException(HzzExceptionEnum.LIMIT_PICKUP_ERROR);
        }
        CustomeraddressinfoVo cai = customeraddressinfoService.getAddressInfoByCaiId(cbiid,caiid);
        if(cai==null){
            throw new HzzBizException(HzzExceptionEnum.GOODS_DEFAULT_ADDRESS_ERROR);
        }
        try {
            long cpuiOrderid = insert(num, cbiid, gbid, (int)caiid);

            Map<String,Object> map=new HashMap<>();
            map.put("cbi_id",cbiid);
            map.put("gbi_id",gbid);
            map.put("cgr_isown",1);
            map.put("cgr_ispickup",0);
            QueryWrapper wrapper_c=new QueryWrapper();
            wrapper_c.allEq(map);
            wrapper_c.in("cgr_islock",0,1);
            //wrapper_c.lt("cgr_buytime", LocalDate.now());
            wrapper_c.orderByAsc("Id");
            wrapper_c.last("limit " + num + " for update");
            List<CustomergoodsrelatedVo> cgrlist = customergoodsrelatedMapper.selectList(wrapper_c);
            if(cgrlist==null||cgrlist.size()<num){
                throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
            }
            for(CustomergoodsrelatedVo cgr:cgrlist){
                cgr.setCgrIspickup(1);
                cgr.setCgrUpdatetime(LocalDateTime.now());
                customergoodsrelatedService.saveCustomergoodsrelated(cgr);
                customerpickupdetailService.savePickUpDetail(cpuiOrderid, cgr.getGiiId());
            }
        } catch (Exception e) {
            log.error("提货更新商品关联表异常", e);
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }

    }
    public void saveCustomerpickupinfo(CustomerpickupinfoVo customerpickupinfoVo) throws HzzBizException {
        if(!saveOrUpdate(customerpickupinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
    public List<CustomerpickupinfoVo> listCustomerpickupinfo(long cbiid) throws HzzBizException {
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.select("cbi_id,gbi_id,cai_id,cpui_status,count(gii_id) as stock");
        wrapper_c.eq("cbi_id",cbiid);
        wrapper_c.groupBy("cbi_id","gbi_id","cai_id","cpui_status","cpui_trackingnumber");
        List<CustomerpickupinfoVo> list = getBaseMapper().selectList(wrapper_c);
        for (CustomerpickupinfoVo cpi : list) {
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(cpi.getGbiId());
            cpi.setGbi(vo);
        }
        return list;
    }

    @Override
    public List<CustomerpickupinfoVo> getCustomerpickupinfos(long cbiid, int pageNum, int pageSize) throws HzzBizException {

        List<CustomerpickupinfoVo> list = lambdaQuery().eq(CustomerpickupinfoVo::getCbiId, cbiid)
                .orderByDesc(CustomerpickupinfoVo::getCpuiCreatetime)
                .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();

        for (CustomerpickupinfoVo cpi : list) {
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(cpi.getGbiId());
            cpi.setGbi(vo);
        }
        return list;
    }

    @Override
    public int getPickUpNum(long cbiid, long gbid) {

        QueryWrapper queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("cbi_id", cbiid);
        queryWrapper.eq("gbi_id", gbid);
        queryWrapper.in("cpui_status", 0, 1);
        queryWrapper.ge("cpui_createtime", LocalDate.now()); // 只查当天的提货个数

        queryWrapper.select("ifnull(sum(cpui_num),0) as total_num ");
        Map<String, Object> map = getMap(queryWrapper);
        int num = Integer.parseInt(String.valueOf(map.get("total_num")));

        return num;
    }

    @Override
    public BigDecimal getPickUpAmount(List<Long> userIds) {

        QueryWrapper<CustomerpickupinfoVo> queryWrapper = new QueryWrapper<CustomerpickupinfoVo>();

        queryWrapper.in("cbi_id", userIds);
        queryWrapper.in("cpui_status", 0, 1);
        queryWrapper.select("gbi_id, ifnull(sum(cpui_num),0) as cpui_num ");
        queryWrapper.groupBy("cbi_id", "gbi_id");
        List<CustomerpickupinfoVo> list = baseMapper.selectList(queryWrapper);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CustomerpickupinfoVo vo : list) {
            try {
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                BigDecimal price = BigDecimal.valueOf(goods.getGbiPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal goodsTotal = price.multiply(BigDecimal.valueOf(vo.getCpuiNum()));
                totalAmount.add(goodsTotal);
            } catch (HzzBizException e) {
                log.error("获取商品信息异常 gbiId={} msg={}", vo.getGbiId(), e.getErrorMsg(), e);
            }
        }
        return totalAmount;
    }

    @Override
    public BigDecimal getPickUpAmountOne(Long userId, boolean isCurrentDay) {

        QueryWrapper<CustomerpickupinfoVo> queryWrapper = new QueryWrapper<CustomerpickupinfoVo>();

        queryWrapper.eq("cbi_id", userId);
        queryWrapper.in("cpui_status", 0, 1);
        if (isCurrentDay) {
            queryWrapper.ge("cpui_createtime", LocalDate.now());
        } else {
            queryWrapper.lt("cpui_createtime", LocalDate.now());
        }
        queryWrapper.select("gbi_id, ifnull(sum(cpui_num),0) as cpui_num ");
        queryWrapper.groupBy("gbi_id");
        List<CustomerpickupinfoVo> list = baseMapper.selectList(queryWrapper);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CustomerpickupinfoVo vo : list) {
            try {
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                BigDecimal price = BigDecimal.valueOf(goods.getGbiPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal goodsTotal = price.multiply(BigDecimal.valueOf(vo.getCpuiNum()));
                totalAmount.add(goodsTotal);
            } catch (HzzBizException e) {
                log.error("获取商品信息异常 gbiId={} msg={}", vo.getGbiId(), e.getErrorMsg(), e);
            }
        }
        return totalAmount;
    }

    public long insert(long num, long cbiid, long gbId, int caiId) {

        CustomerpickupinfoVo cpi=new CustomerpickupinfoVo();
        cpi.setCpuiTrackingnumber("");
        cpi.setCpuiTracktype("");
        cpi.setCpuiNum(num);
        cpi.setCbiId(cbiid);
        cpi.setGbiId(gbId);
        cpi.setCaiId(caiId);
        cpi.setCpuiCreatetime(LocalDateTime.now());
        cpi.setCpuiUpdatetime(LocalDateTime.now());
        cpi.setCpuiFinishtime(LocalDateTime.now());
        cpi.setCpuiStatus(0);
        customerpickupinfoMapper.insert(cpi);
        return cpi.getCpuiOrderid();
    }
}



