package com.akfly.hzz.conroller;


import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.service.BroadcastnoteinfoService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.service.PictureinfoService;
import com.akfly.hzz.util.CacheUtils;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.BroadcastnoteinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.PictureinfoVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/sy")
public class ShouYeController {
    @Autowired
    GoodsbaseinfoService goodsbaseinfoService;
    @Autowired
    BroadcastnoteinfoService broadcastnoteinfoService;
    @Autowired
    PictureinfoService pictureinfoService;

    /**
     * 兑换商品本期不实现
     * 头图和活动区跳转，商品详情页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        try {
            return CacheUtils.graphs.get(CacheUtils.CACHEUTILS_SY, () -> shouye());
        } catch (Exception e) {
            log.error("ShouYeController index error", e);
        }
        return shouye();

    }

    @GetMapping(value = "/goodList")
    public String goodList(@Validated int beg, int size) {
        List<GoodsbaseinfoVo> zcgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, CommonConstant.GOODSTYPE_ZC)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit " + beg + "," + size + " ").list();
        return JsonUtils.toJson(zcgoods);

    }

    @GetMapping(value = "/goodInfo")
    public String goodInfo(@Validated String goodId) {
        Map<String, Object> map = Maps.newHashMap();
        GoodsbaseinfoVo goodsbaseinfoVo = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiId, goodId).one();
        map.put("gbi", goodsbaseinfoVo);
        if (goodsbaseinfoVo == null) {
            return JsonUtils.toJson(map);
        }
        List<PictureinfoVo> pictureinfoVos = pictureinfoService.lambdaQuery()
                .eq(PictureinfoVo::getGbiId, goodsbaseinfoVo.getGbiId()).list();
        map.put("pivs", pictureinfoVos);
        return JsonUtils.toJson(map);

    }

    @GetMapping(value = "/xiaoxiList")
    public String xiaoxiList() {
        List<BroadcastnoteinfoVo> tzs = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_TZ)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 10").list();
        return JsonUtils.toJson(tzs);

    }


    private String shouye() {
        List<GoodsbaseinfoVo> zcgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, CommonConstant.GOODSTYPE_ZC)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit 3").list();

        List<GoodsbaseinfoVo> xsgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, CommonConstant.GOODSTYPE_XS)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit 3").list();

        List<BroadcastnoteinfoVo> sys = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_SY)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 3").list();

        List<BroadcastnoteinfoVo> tzs = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_TZ)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 5").list();

        List<BroadcastnoteinfoVo> hds = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_HD)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 1").list();
        Map<String, Object> map = Maps.newHashMap();
        map.put("sys", sys);
        map.put("tzs", tzs);
        map.put("hds", hds);
        map.put("zcgoods", zcgoods);
        map.put("xsgoods", xsgoods);
        return JsonUtils.toJson(map);
    }
}
