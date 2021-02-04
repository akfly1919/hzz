package com.akfly.hzz.service;

import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户商品物料对应表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomergoodsrelatedService extends IService<CustomergoodsrelatedVo> {

    /**
     * @Description
     * @param  gbiId 商品id
     * @rerurn
     * @throws
     * @author
     * @date 2021/1/30 15:17
     */
    int getStock(long gbiId);

    public CustomergoodsrelatedVo selectCustomergoodsrelatedVoForUpdate(Map<String,Object> condition);

    public void saveCustomergoodsrelated(CustomergoodsrelatedVo customergoodsrelatedVo) throws HzzBizException;

    List<UserGoodsDto> getStockForUser(Long userId, StockEnum stockEnum, PickUpEnum pickUpEnum) throws HzzBizException;

}
