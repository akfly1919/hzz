package com.akfly.hzz.service.impl;

import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.mapper.GoodsbaseinfoMapper;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 商品基础信息-用于展示 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class GoodsbaseinfoServiceImpl extends ServiceImpl<GoodsbaseinfoMapper, GoodsbaseinfoVo> implements GoodsbaseinfoService {
    @Resource
    GoodsbaseinfoMapper goodsbaseinfoMapper;

    public GoodsbaseinfoVo getGoodsbaseinfoVo(long gbiId){
        QueryWrapper<GoodsbaseinfoVo> contract_wrapper = new QueryWrapper<GoodsbaseinfoVo>();
        contract_wrapper.eq("gbi_id",gbiId);
        return goodsbaseinfoMapper.selectOne(contract_wrapper);
    }

}
