package com.akfly.hzz.service;

import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
