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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping(value = "/hello")
    public String hello(){
        return redisTemplate.opsForValue().get("a")+"";
    }

    /**
     * 兑换商品本期不实现
     * 头图和活动区跳转，商品详情页
     *
     * @return
     */
    @ApiOperation(value="首页所有内容查询接口",notes="不需要登录")
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        try {
            return CacheUtils.graphs.get(CacheUtils.CACHEUTILS_SY, () -> shouye());
        } catch (Exception e) {
            log.error("ShouYeController index error", e);
        }
        return shouye();

    }

    @ApiOperation(value="商品列表查询",notes="不需要登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="起始位置",required=true),
            @ApiImplicitParam(name="size",value="每页展示多少条数据",required=true),
            @ApiImplicitParam(name="gbiType",value="商品类型(1:普通商品, 2:新手商品)",required=true),
    })
    @RequestMapping(value = "/goodList", method = {RequestMethod.GET, RequestMethod.POST})
    public String goodList(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg, @RequestParam @Digits(integer = 10,fraction = 0) Integer size, @RequestParam @Digits(integer = 2,fraction = 0)Integer gbiType) {
        List<GoodsbaseinfoVo> zcgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, gbiType)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit " + beg + "," + size + " ").list();
        return JsonUtils.toJson(zcgoods);

    }

    @ApiOperation(value="商品明细查询",notes="不需要登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodId",value="商品id",required=true)
    })
    @RequestMapping(value = "/goodInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String goodInfo(@RequestParam @NotEmpty String goodId) {
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

    @ApiOperation(value="通知消息查询",notes="不需要登录")
    @RequestMapping(value = "/xiaoxiList", method = {RequestMethod.GET, RequestMethod.POST})
    public String xiaoxiList() {
        List<BroadcastnoteinfoVo> tzs = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_TZ)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 10").list();
        return JsonUtils.toJson(tzs);

    }


    private String shouye() {
        //正常商品
        List<GoodsbaseinfoVo> zcgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, CommonConstant.GOODSTYPE_ZC)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit 3").list();
        //新手商品
        List<GoodsbaseinfoVo> xsgoods = goodsbaseinfoService.lambdaQuery()
                .eq(GoodsbaseinfoVo::getGbiType, CommonConstant.GOODSTYPE_XS)
                .orderByDesc(GoodsbaseinfoVo::getGbiSort).last("limit 3").list();
        //首页轮播图
        List<BroadcastnoteinfoVo> sys = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_SY)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 3").list();
        //消息通知
        List<BroadcastnoteinfoVo> tzs = broadcastnoteinfoService.lambdaQuery()
                .eq(BroadcastnoteinfoVo::getBniPostion, CommonConstant.LUNBO_TZ)
                .orderByDesc(BroadcastnoteinfoVo::getBniSort).last("limit 5").list();
        //活动图
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
