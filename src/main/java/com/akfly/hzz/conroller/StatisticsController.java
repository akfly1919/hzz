package com.akfly.hzz.conroller;

import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.GoodInfoDto;
import com.akfly.hzz.dto.GoodsbaseinfoDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.service.ReporttradedateService;
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


    @ApiOperation(value="行情页买入查询",notes="需要登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodId",value="商品id",required=true)
    })
    @PostMapping(value = "/goodsInfoForBuy")
    public BaseRspDto<GoodsbaseinfoDto> goodsInfoForBuy(@RequestParam @NotEmpty String goodId) {

        BaseRspDto<GoodsbaseinfoDto> rsp = new BaseRspDto<GoodsbaseinfoDto>();
        try {
            GoodsbaseinfoVo goodsbaseinfoVo = goodsbaseinfoService.lambdaQuery()
                    .eq(GoodsbaseinfoVo::getGbiId, goodId).one();
            GoodsbaseinfoDto dto = new GoodsbaseinfoDto();
            BeanUtils.copyProperties(goodsbaseinfoVo, dto);
            int stock = customergoodsrelatedService.getStock(goodsbaseinfoVo.getGbiId());
            dto.setStock(stock);
            rsp.setData(dto);
        } catch (Exception e) {
            log.error("获取商品详情系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;

    }
}
