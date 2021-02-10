package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.GoodInfoDto;
import com.akfly.hzz.dto.GoodsbaseinfoDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.service.TradegoodsellService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.PictureinfoVo;
import com.akfly.hzz.vo.ReporttradedateVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/statistics")
@Validated
public class StatisticsController {

    @Autowired
    ReporttradedateService seporttradedateService;

    @Autowired
    GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;

    @Resource
    private TradegoodsellService tradegoodsellService;

    @ApiOperation(value="交易行情",notes="交易行情查询")
    @PostMapping
    BaseRspDto<Map<String,Object>> listReporttradedateStatistics(@RequestParam @NotNull Long gbid, @RequestParam @Pattern(regexp = "YEAR|MONTH|DAY|WEEK|HOUR") String queryType){

        BaseRspDto<Map<String,Object>> rsp = new BaseRspDto<Map<String,Object>>();
        try {
            Map<String,Object> data=new HashMap<>();

            GoodsbaseinfoVo gbi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
            List<ReporttradedateVo> list = seporttradedateService.listReporttradedateStatistics(gbid.longValue(), queryType);
            data.put("gbi",gbi);
            data.put("list",list);
            rsp.setData(data);
        }catch (Exception e){
            e.printStackTrace();
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="行情页买入/卖出查询",notes="不需要登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodId",value="商品id",required=true),
            @ApiImplicitParam(name="type",value="类型 (1: 买入 2: 卖出)",required=true)
    })
    @PostMapping(value = "/goodsInfoForTrade")
    @VerifyToken
    public BaseRspDto<UserGoodsDto> goodsInfoForTrade(@RequestParam @NotNull Long goodId, Integer type) {

        BaseRspDto<UserGoodsDto> rsp = new BaseRspDto<UserGoodsDto>();
        UserGoodsDto userGoodsDto;
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            if (type == 1) {
                GoodsbaseinfoVo goodsbaseinfoVo = goodsbaseinfoService.lambdaQuery()
                        .eq(GoodsbaseinfoVo::getGbiId, goodId).one();
                userGoodsDto = new UserGoodsDto();
                BeanUtils.copyProperties(goodsbaseinfoVo, userGoodsDto);
                //int stock = tradegoodsellService.getSellVolume(goodId);
                int stock =customergoodsrelatedService.getStock(goodId);
                userGoodsDto.setStock((long)stock);
                userGoodsDto.setCbiid(userInfo.getCbiId());
            } else if (type == 2) {
                userGoodsDto = customergoodsrelatedService.getCanSellOfGbi(userInfo.getCbiId(), goodId);
            } else {
                throw new HzzBizException(HzzExceptionEnum.NOT_SUPPORT_TYPE_ERROR);
            }
            rsp.setData(userGoodsDto);
        } catch (Exception e) {
            log.error("获取商品详情系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;

    }
}
