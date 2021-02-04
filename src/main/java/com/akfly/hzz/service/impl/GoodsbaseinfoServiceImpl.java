package com.akfly.hzz.service.impl;

import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RedisUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.mapper.GoodsbaseinfoMapper;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品基础信息-用于展示 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
@Slf4j
public class GoodsbaseinfoServiceImpl extends ServiceImpl<GoodsbaseinfoMapper, GoodsbaseinfoVo> implements GoodsbaseinfoService {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    GoodsbaseinfoMapper goodsbaseinfoMapper;

    public GoodsbaseinfoVo getGoodsbaseinfoVo(long gbiId){
        QueryWrapper<GoodsbaseinfoVo> contract_wrapper = new QueryWrapper<GoodsbaseinfoVo>();
        contract_wrapper.eq("gbi_id",gbiId);
        return goodsbaseinfoMapper.selectOne(contract_wrapper);
    }

    @Override
    public GoodsbaseinfoVo getGoodsbaseinfoWithRedis(long gbiId) throws HzzBizException {


        String key = CommonConstant.GOODS_PREFIX + gbiId;
        Object o = redisUtils.get(key);

        if (o == null) {
            QueryWrapper<GoodsbaseinfoVo> contract_wrapper = new QueryWrapper<GoodsbaseinfoVo>();
            contract_wrapper.eq("gbi_id",gbiId);
            GoodsbaseinfoVo vo = goodsbaseinfoMapper.selectOne(contract_wrapper);
            if (vo == null) throw new HzzBizException(HzzExceptionEnum.GOODS_NOTEXIST_ERROR);
            redisUtils.set(key, JsonUtils.toJson(vo), 2 * 60 *60);
            return vo;
        } else {
            log.warn("从redis获取商品信息vo={}", JsonUtils.toJson(o));
            GoodsbaseinfoVo vo = JsonUtils.toBean(o.toString(), GoodsbaseinfoVo.class);
            return vo;
        }

    }

}
