package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.UserAllAssetDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.dto.UserGoodsWithPickUpDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RedisUtils;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 客户商品物料对应表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class CustomergoodsrelatedServiceImpl extends ServiceImpl<CustomergoodsrelatedMapper, CustomergoodsrelatedVo> implements CustomergoodsrelatedService {

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public int getStock(long gbiId) {

        //String key = CommonConstant.GOODS_STOCK_PREFIX + gbiId;
        //Object o = redisUtils.get(key);

        // 获取已锁定的库存，说明正在卖的单子，是市场上可以买入的库存量
        int stock = lambdaQuery().eq(CustomergoodsrelatedVo::getGbiId, gbiId)
                    .eq(CustomergoodsrelatedVo::getCgrIsown, 1).in(CustomergoodsrelatedVo::getCgrIslock, 2)
                    .eq(CustomergoodsrelatedVo::getCgrIspickup, 0).count();
            //redisUtils.set(key, stock, 60 *60);
        return stock;

    }

    public CustomergoodsrelatedVo selectCustomergoodsrelatedVoForUpdate(Map<String,Object> condition){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.allEq(condition);
        wrapper.last("for update");
        return getBaseMapper().selectOne(wrapper);
    }

    public void saveCustomergoodsrelated(CustomergoodsrelatedVo customergoodsrelatedVo) throws HzzBizException {
        if(!saveOrUpdate(customergoodsrelatedVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    @Override
    public List<UserGoodsDto> getStockForUser(Long userId, StockEnum stockEnum, PickUpEnum pickUpEnum) throws HzzBizException {

        QueryWrapper<CustomergoodsrelatedVo> queryWrapper = new QueryWrapper<CustomergoodsrelatedVo>();
        queryWrapper.eq("cbi_id", userId).eq("cgr_isown", 1);
        if (StockEnum.UNLOCKED.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 0);
        } else if (StockEnum.LOCKED.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 2);
        } else if (StockEnum.FROZEN.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 1);
        } else if (StockEnum.XIANHUO.equals(stockEnum)) {
            queryWrapper.in("cgr_islock", Arrays.asList(0, 1));
        }
        if (PickUpEnum.PICK.equals(pickUpEnum)) {
            queryWrapper.eq("cgr_ispickup", 1);
        } else {
            queryWrapper.eq("cgr_ispickup", 0);
        }
        queryWrapper.select("cbi_id, gbi_id, count(id) as stock");
        queryWrapper.groupBy("gbi_id");

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);

        List<UserGoodsDto> userGoodsDtoList = new ArrayList<>();
        for (Map<String, Object> temp : list) {
            if (StringUtils.isEmpty(String.valueOf(temp.get("gbi_id")))) continue;
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(Long.parseLong(String.valueOf(temp.get("gbi_id"))));
            UserGoodsDto userGoodsDto = new UserGoodsDto();
            userGoodsDto.setCbiid(userId);
            userGoodsDto.setStock(Long.valueOf(String.valueOf(temp.get("stock"))).intValue());
            BeanUtils.copyProperties(vo, userGoodsDto);
            userGoodsDtoList.add(userGoodsDto);
        }
        return userGoodsDtoList;
    }

    public List<CustomergoodsrelatedVo> listStockCanSold(long cbiid){
        return getBaseMapper().listStockCanSold(cbiid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void  unlock() throws HzzBizException {

        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.plusDays(-14);
        log.info("解锁用户库存信息定时启动......start={}, end={}", start, end);
        List<CustomergoodsrelatedVo> voList = lambdaQuery().eq(CustomergoodsrelatedVo::getCgrIslock, 1)
                .eq(CustomergoodsrelatedVo::getCgrIsown, 1).eq(CustomergoodsrelatedVo::getCgrIspickup, 0)
                .between(CustomergoodsrelatedVo::getCgrBuytime, start, end).list();

        for(CustomergoodsrelatedVo cgr : voList){
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(cgr.getGbiId());
            //int frozenDays = (vo.getGbiFrozendays() == null ? 1 : vo.getGbiFrozendays());

            if (vo.getGbiFrozendays() == null) {
                log.error("商品信息中的售卖冻结天数为空 gbiId={}", cgr.getGbiId());
            } else {
                if (cgr.getCgrBuytime().plusDays(vo.getGbiFrozendays()).compareTo(LocalDateTime.now()) <= 0) {
                    cgr.setCgrIslock(0);
                    saveCustomergoodsrelated(cgr);
                }
            }
        }
    }

    @Override
    public UserGoodsWithPickUpDto getCanPickUpOfGbi(long cbiid, long gbiid) throws HzzBizException {

        List<CustomergoodsrelatedVo> list = lambdaQuery().eq(CustomergoodsrelatedVo::getCbiId, cbiid)
                .eq(CustomergoodsrelatedVo::getGbiId, gbiid).eq(CustomergoodsrelatedVo::getCgrIspickup, 0)
                .in(CustomergoodsrelatedVo::getCgrIslock, Arrays.asList(0, 1))
                .eq(CustomergoodsrelatedVo::getCgrIsown, 1).list();

        UserGoodsWithPickUpDto userGoodsDto = new UserGoodsWithPickUpDto();
        try {
            int stock = list.size();
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(gbiid);
            userGoodsDto.setCbiid(cbiid);
            if (!CollectionUtils.isEmpty(list)) {
                CustomergoodsrelatedVo temp = list.get(0);
                userGoodsDto.setCgrBuytime(temp.getCgrBuytime());
                userGoodsDto.setCgrForzentime(temp.getCgrForzentime());
                userGoodsDto.setCgrSelltime(temp.getCgrSelltime());
            }
            userGoodsDto.setStock(stock);
            BeanUtils.copyProperties(vo, userGoodsDto);
        } catch (HzzBizException e) {
            log.error("从redis获取商品信息异常 msg={}", e.getErrorMsg(), e);
        } catch (Exception e) {
            log.error("获取提货明细异常", e);
            throw new HzzBizException(HzzExceptionEnum.SYSTEM_ERROR);
        }
        return userGoodsDto;
    }

    @Override
    public UserGoodsDto getCanSellOfGbi(long cbiid, long gbiid) throws HzzBizException {

        int stock = lambdaQuery().eq(CustomergoodsrelatedVo::getCbiId, cbiid)
                .eq(CustomergoodsrelatedVo::getGbiId, gbiid).eq(CustomergoodsrelatedVo::getCgrIspickup, 0)
                .in(CustomergoodsrelatedVo::getCgrIslock, 0)
                .eq(CustomergoodsrelatedVo::getCgrIsown, 1).count();

        UserGoodsDto userGoodsDto = new UserGoodsDto();
        try {
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(gbiid);
            userGoodsDto.setCbiid(cbiid);
            userGoodsDto.setStock(stock);
            BeanUtils.copyProperties(vo, userGoodsDto);
        } catch (HzzBizException e) {
            log.error("从redis获取商品信息异常 msg={}", e.getErrorMsg(), e);
        } catch (Exception e) {
            log.error("获取用户可卖出商品信息异常", e);
            throw new HzzBizException(HzzExceptionEnum.SYSTEM_ERROR);
        }
        return userGoodsDto;

    }

    @Override
    public Map<String, Long> getStockForMyself(Long userId, StockEnum stockEnum, PickUpEnum pickUpEnum) {

        QueryWrapper<CustomergoodsrelatedVo> queryWrapper = new QueryWrapper<CustomergoodsrelatedVo>();
        queryWrapper.eq("cbi_id", userId).eq("cgr_isown", 1);
        if (StockEnum.UNLOCKED.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 0);
        } else if (StockEnum.LOCKED.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 1);
        } else if (StockEnum.FROZEN.equals(stockEnum)) {
            queryWrapper.eq("cgr_islock", 2);
        } else if (StockEnum.XIANHUO.equals(stockEnum)) {
            queryWrapper.in("cgr_islock", Arrays.asList(0, 1));
        }
        if (PickUpEnum.PICK.equals(pickUpEnum)) {
            queryWrapper.eq("cgr_ispickup", 1);
        } else {
            queryWrapper.eq("cgr_ispickup", 0);
        }
        queryWrapper.select(" cgr_islock, count(id) as stock");
        queryWrapper.groupBy("cgr_islock");

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        Map<String, Long> result = new HashMap<>();
        long stock = 0;
        long frozenStock = 0;
        for (Map<String, Object> temp : list) {
            int cgr_isLock = (int) temp.get("cgr_islock");
            if (cgr_isLock == 0) {
                long unFrozenStock =  (long) temp.get("stock");
                stock = stock + unFrozenStock;
            } else if (cgr_isLock == 1) {
                frozenStock =  (long) temp.get("stock");
                stock = stock + frozenStock;
            }
        }
        result.put("frozenStock", frozenStock);
        result.put("stock", stock);
        return result;
    }

    @Override
    public int getMyStock(Long userId) {

        int stock = lambdaQuery().eq(CustomergoodsrelatedVo::getCbiId, userId).eq(CustomergoodsrelatedVo::getCgrIsown, 1)
                .eq(CustomergoodsrelatedVo::getCgrIspickup, 0).count();
        return stock;
    }

    @Override
    public UserAllAssetDto getMyAsset(Long userId) throws HzzBizException {

        QueryWrapper<CustomergoodsrelatedVo> queryWrapper = new QueryWrapper<CustomergoodsrelatedVo>();
        queryWrapper.eq("cbi_id", userId).eq("cgr_isown", 1);
        queryWrapper.eq("cgr_ispickup", 0);
        queryWrapper.select("cbi_id, gbi_id, count(id) as stock");
        queryWrapper.groupBy("gbi_id");

        try {
            List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
            int stock = 0;
            BigDecimal asset = BigDecimal.ZERO;
            for (Map<String, Object> temp : list) {
                String gbiId = String.valueOf(temp.get("gbi_id"));
                if (StringUtils.isEmpty(gbiId)) continue;
                GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(Long.parseLong(gbiId));
                stock = stock + Long.valueOf(String.valueOf(temp.get("stock"))).intValue();
                asset = asset.add(BigDecimal.valueOf(vo.getGbiPrice()).multiply(BigDecimal.valueOf(stock)));
            }
            UserAllAssetDto userAllAssetDto = new UserAllAssetDto();
            userAllAssetDto.setAsset(asset);
            userAllAssetDto.setStock(stock);
            log.info("获取用户总资产 userId={}, stock={}, asset={}", userId, userAllAssetDto.getStock(), userAllAssetDto.getAsset());
            return userAllAssetDto;
        } catch (Exception e) {
            log.error("获取用户总资产异常 userId={}", userId, e);
            throw new HzzBizException(HzzExceptionEnum.SYSTEM_ERROR);
        }
    }
}
