package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品基础信息-用于展示 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface GoodsbaseinfoService extends IService<GoodsbaseinfoVo> {
    public GoodsbaseinfoVo getGoodsbaseinfoVo(long gbiId);

    GoodsbaseinfoVo getGoodsbaseinfoWithRedis(long gbiId) throws HzzBizException;
}
