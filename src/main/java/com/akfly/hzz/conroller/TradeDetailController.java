package com.akfly.hzz.conroller;


import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.HadSystemSellDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.TradegoodsellService;
import com.akfly.hzz.service.TradepredictinfoService;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/tradeDetail")
public class TradeDetailController {

    @Resource
    private TradepredictinfoService tradepredictinfoService;

    @Resource
    private TradegoodsellService tradegoodsellService;


    @ApiOperation(value="获取是否有系统预卖单",notes="不要求用户登录(0代表没有系统预卖单，1代表有)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gbiid",value="商品id",required=true)
    })
    @PostMapping(value = "/hadSystemSell")
    public BaseRspDto<HadSystemSellDto> hadSystemSell(@RequestParam @NotNull Long gbiid) {

        BaseRspDto<HadSystemSellDto> rsp = new BaseRspDto<HadSystemSellDto>();
        try {
            int flag = tradegoodsellService.hadSystemSell(gbiid);
            HadSystemSellDto dto = new HadSystemSellDto();
            dto.setFlag(flag);
            rsp.setData(dto);
        } catch (Exception e) {
            log.error("获取是否有系统预卖单系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="获取指定商品的预卖单明细",notes="不要求用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gbiid",value="商品id",required=true),
            @ApiImplicitParam(name="beg",value="起始位置(从0开始)",required=true),
            @ApiImplicitParam(name="size",value="每页展示多少条数据",required=true)
    })
    @PostMapping(value = "/getSellDetails")
    public BaseRspDto<List<TradegoodsellVo>> getSellDetails(@RequestParam @NotNull Long gbiid,
                                                       @RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                       @RequestParam @Digits(integer = 10,fraction = 0) Integer size) {

        BaseRspDto<List<TradegoodsellVo>> rsp = new BaseRspDto<List<TradegoodsellVo>>();
        try {
            List<TradegoodsellVo> tradegoodsellVoList = tradegoodsellService.getSellDetail(gbiid,size, beg);
            HadSystemSellDto dto = new HadSystemSellDto();
            rsp.setData(tradegoodsellVoList);
        } catch (Exception e) {
            log.error("获取指定商品的预卖单明细系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="获取指定商品的预买单明细",notes="不要求用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gbiid",value="商品id",required=true),
            @ApiImplicitParam(name="beg",value="起始位置(从0开始)",required=true),
            @ApiImplicitParam(name="size",value="每页展示多少条数据",required=true)
    })
    @PostMapping(value = "/getBuyDetails")
    public BaseRspDto<List<TradepredictinfoVo>> getBuyDetails(@RequestParam @NotNull Long gbiid,
                                                      @RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                      @RequestParam @Digits(integer = 10,fraction = 0) Integer size) {

        BaseRspDto<List<TradepredictinfoVo>> rsp = new BaseRspDto<List<TradepredictinfoVo>>();
        try {
            List<TradepredictinfoVo> tradepredictinfoVoList = tradepredictinfoService.getBuyDetails(gbiid, size, beg);
            rsp.setData(tradepredictinfoVoList);
        } catch (Exception e) {
            log.error("获取指定商品的预买单明细系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

}
