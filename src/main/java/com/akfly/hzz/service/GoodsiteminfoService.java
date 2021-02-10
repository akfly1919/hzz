package com.akfly.hzz.service;

import com.akfly.hzz.vo.GoodsiteminfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品物料信息-最细颗粒度 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface GoodsiteminfoService extends IService<GoodsiteminfoVo> {

    int getPlatFormStock(long gbiId);

}
