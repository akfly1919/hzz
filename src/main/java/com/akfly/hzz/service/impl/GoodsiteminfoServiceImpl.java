package com.akfly.hzz.service.impl;

import com.akfly.hzz.vo.GoodsiteminfoVo;
import com.akfly.hzz.mapper.GoodsiteminfoMapper;
import com.akfly.hzz.service.GoodsiteminfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品物料信息-最细颗粒度 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class GoodsiteminfoServiceImpl extends ServiceImpl<GoodsiteminfoMapper, GoodsiteminfoVo> implements GoodsiteminfoService {

    @Override
    public int getPlatFormStock(long cbiId) {

        log.info("GoodsiteminfoServiceImpl 获取商品大盘库存数量 cbiId={}", cbiId);
        int stock = lambdaQuery().eq(GoodsiteminfoVo::getGiiIspickup, 0).eq(GoodsiteminfoVo::getGiiStatus, 1).count();
        return stock;
    }

}
