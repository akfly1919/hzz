package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户商品物料对应表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomergoodsrelatedServiceImpl extends ServiceImpl<CustomergoodsrelatedMapper, CustomergoodsrelatedVo> implements CustomergoodsrelatedService {

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Override
    public int getStock(long gbiId) {

        int stock = lambdaQuery().eq(CustomergoodsrelatedVo::getGbiId, gbiId)
                .eq(CustomergoodsrelatedVo::getCgrIsown, 1).eq(CustomergoodsrelatedVo::getCgrIslock, 0)
                .eq(CustomergoodsrelatedVo::getCgrIspickup, 0).count();
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
        queryWrapper.select("cbi_id, gbi_id, count(id) as stock");
        queryWrapper.groupBy("gbi_id");

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);

        List<UserGoodsDto> userGoodsDtoList = new ArrayList<>();
        for (Map<String, Object> temp : list) {
            if (StringUtils.isEmpty(String.valueOf(temp.get("gbi_id")))) continue;
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(Long.parseLong(String.valueOf(temp.get("gbi_id"))));
            UserGoodsDto userGoodsDto = new UserGoodsDto();
            userGoodsDto.setCbiid(userId);
            userGoodsDto.setStock((long) temp.get("stock"));
            BeanUtils.copyProperties(vo, userGoodsDto);
            userGoodsDtoList.add(userGoodsDto);
        }
        return userGoodsDtoList;
    }
}
