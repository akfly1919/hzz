package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.GoodsbaseinfoDto;
import com.akfly.hzz.dto.TradeInfoDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/trade")
@Validated
public class TradeInfoController {

    @Resource
    private TradeorderinfoService tradeorderinfoService;
    @Resource
    private TradegoodsellService tradegoodsellService;

    @Resource
    private TradepredictinfoService tradepredictinfoService;

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;

    @ApiOperation(value="获取已成交订单",notes="用户登录")
    @PostMapping(value = "/getFinishedTradeOrder")
    @VerifyToken
    public BaseRspDto<List<TradeorderinfoVo>> getTradeOrder(int beg, int size, Date beginTime, Date endTime){
        BaseRspDto<List<TradeorderinfoVo>> rsp = new BaseRspDto<List<TradeorderinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<TradeorderinfoVo> list = tradeorderinfoService.getTradeorderinfoVo(beg, size, userInfo.getCbiId(), beginTime, endTime);
            rsp.setData(list);
        } catch (HzzBizException e) {
            log.error("获取购买订单业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取购买订单系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @ApiOperation(value="买入",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="price",value="买入价格"),
            @ApiImplicitParam(name="gbid",value="商品id",required=true),
            @ApiImplicitParam(name="num",value="买入数量",required=true),
            @ApiImplicitParam(name="type",value="买入类型(1: 委托买入 2: 正常买入)",required=true),
            @ApiImplicitParam(name="isOnSale",value="是否特价商品",required=false)

    })
    @PostMapping(value = "/buy")
    @VerifyToken
    public BaseRspDto<String> buy(@RequestParam @Digits(integer = 6,fraction = 2) Double price,
                                  @RequestParam @NotNull Long gbid,@RequestParam @NotNull Integer num,
                                  @RequestParam @NotNull Integer type,@RequestParam(defaultValue = "false") boolean isOnSale){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            tradeorderinfoService.nomalBuy(userInfo.getCbiId(), gbid, num, price,isOnSale,type, userInfo.getCbiIsnew());

        } catch (HzzBizException e) {
            log.error("购买业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("购买系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @PostMapping(value = "/cancel")
    @VerifyToken
    public BaseRspDto<String> cancel(
            @RequestParam @NotNull String orderid, @RequestParam @Min(1)@Max(2) int tradeType){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            if(tradeType==1){
                tradepredictinfoService.cancel(orderid);
            }else if(tradeType==2){
                tradegoodsellService.cancel(orderid);
            }

        } catch (HzzBizException e) {
            log.error("购买业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("购买系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    @ApiOperation(value="卖出",notes="用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="price",value="卖出价格",required=true),
            @ApiImplicitParam(name="gbid",value="商品id",required=true),
            @ApiImplicitParam(name="num",value="卖出数量",required=true),
            @ApiImplicitParam(name="type",value="卖出类型(1: 委托卖出 2: 正常卖出)",required=true)
    })
    @PostMapping(value = "/sell")
    @VerifyToken
    public BaseRspDto<String> sell(@RequestParam @NotNull @Digits(integer = 6,fraction = 2) Double price, @RequestParam @NotNull Long gbid,
                                   @RequestParam @NotNull Integer num, @RequestParam @NotNull Integer type){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
             tradegoodsellService.sell(userInfo.getCbiId(), gbid, num, price);

        } catch (HzzBizException e) {
            log.error("卖出业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("卖出系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    //@PostMapping(value = "/listCanSold")
    //@VerifyToken
    //public BaseRspDto<List<CustomergoodsrelatedVo>> listCanSold(){
    //    BaseRspDto<List<CustomergoodsrelatedVo>> rsp = new BaseRspDto<List<CustomergoodsrelatedVo>>();
    //    try {
    //        CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
    //        List<CustomergoodsrelatedVo>    list=customergoodsrelatedService.listStockCanSold(userInfo.getCbiId());
    //        rsp.setData(list);
    //    /*} catch (HzzBizException e) {
    //        log.error("卖出业务错误 msg={}", e.getErrorMsg(), e);
    //        rsp.setCode(e.getErrorCode());
    //        rsp.setMsg(e.getErrorMsg());*/
    //    } catch (Exception e) {
    //        log.error("卖出系统异常", e);
    //        rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
    //        rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
    //    }
    //    return rsp;
    //}

    @ApiOperation(value="获取用户可以卖出的商品信息(不分页)",notes="用户登录就可以")
    @PostMapping(value = "/getCanSellStocks")
    @VerifyToken
    public BaseRspDto<List<UserGoodsDto>> getCanSellStocks(){

        BaseRspDto<List<UserGoodsDto>> rsp = new BaseRspDto<List<UserGoodsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<UserGoodsDto> userGoodsDtoList = customergoodsrelatedService.getStockForUser(userInfo.getCbiId(), StockEnum.UNLOCKED, PickUpEnum.UNPICK);
            rsp.setData(userGoodsDtoList);
        } catch (HzzBizException e) {
            log.error("获取用户持仓信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取用户持仓信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    @ApiOperation(value="获取用户委托订单(所有订单)",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tradeType",value="交易类型(1: 买入交易 2: 卖出交易)",required=true),
            @ApiImplicitParam(name="beg",value="第几页",required=true),
            @ApiImplicitParam(name="size",value="每页多少条",required=true)

    })
    @PostMapping(value = "/getTradeOrder")
    @VerifyToken
    public BaseRspDto<List<TradeInfoDto>> getBuyTradeOrder(int tradeType, int beg, int size){

        BaseRspDto<List<TradeInfoDto>> rsp = new BaseRspDto<List<TradeInfoDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<TradeInfoDto> respList;
            if (tradeType == 1) {
                List<TradepredictinfoVo> list = tradepredictinfoService.getBuyTrade(userInfo.getCbiId(), size, beg);
                respList = buildBuyTradeRespList(list);
            } else if (tradeType == 2) {
                List<TradegoodsellVo> list = tradegoodsellService.getSellTrade(userInfo.getCbiId(), size, beg);
                respList = buildSellTradeRespList(list);
            } else {
                throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
            }
            rsp.setData(respList);
        } catch (HzzBizException e) {
            log.error("获取用户委托订单业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取用户委托订单系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    
    @ApiOperation(value="获取用户冻结商品信息(不分页)",notes="用户登录就可以")
    @PostMapping(value = "/getFrozenStocks")
    @VerifyToken
    public BaseRspDto<List<UserGoodsDto>> getFrozenStocks(){

        BaseRspDto<List<UserGoodsDto>> rsp = new BaseRspDto<List<UserGoodsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<UserGoodsDto> userGoodsDtoList = customergoodsrelatedService.getStockForUser(userInfo.getCbiId(), StockEnum.FROZEN, PickUpEnum.UNPICK);
            rsp.setData(userGoodsDtoList);
        } catch (HzzBizException e) {
            log.error("获取用户冻结商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取用户冻结商品信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    @ApiOperation(value="获取用户现货商品信息(不分页)",notes="用户登录就可以")
    @PostMapping(value = "/getXianhuoStocks")
    @VerifyToken
    public BaseRspDto<List<UserGoodsDto>> getXianhuoStocks(){

        BaseRspDto<List<UserGoodsDto>> rsp = new BaseRspDto<List<UserGoodsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<UserGoodsDto> userGoodsDtoList = customergoodsrelatedService.getStockForUser(userInfo.getCbiId(), StockEnum.XIANHUO, PickUpEnum.UNPICK);
            rsp.setData(userGoodsDtoList);
        } catch (HzzBizException e) {
            log.error("获取用户现货商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取用户现货商品信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @ApiOperation(value="获取用户可提货列表",notes="用户登录就可以")
    @PostMapping(value = "/getCanPcikupStocks")
    @VerifyToken
    public BaseRspDto<List<UserGoodsDto>> getCanPcikupStocks(){

        BaseRspDto<List<UserGoodsDto>> rsp = new BaseRspDto<List<UserGoodsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<UserGoodsDto> userGoodsDtoList = customergoodsrelatedService.getStockForUser(userInfo.getCbiId(), StockEnum.XIANHUO, PickUpEnum.UNPICK);
            rsp.setData(userGoodsDtoList);
        } catch (HzzBizException e) {
            log.error("获取用户现货商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("获取用户现货商品信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    private List<TradeInfoDto> buildBuyTradeRespList(List<TradepredictinfoVo> list) throws HzzBizException {

        List<TradeInfoDto> respList = new ArrayList<>();
        for (TradepredictinfoVo vo : list) {
            TradeInfoDto tradeInfoDto = new TradeInfoDto();
            tradeInfoDto.setId(vo.getId());
            tradeInfoDto.setOrderId(vo.getTpiId());
            tradeInfoDto.setGbiId(vo.getGbiId());
            GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
            tradeInfoDto.setGbiName(goods.getGbiName());
            tradeInfoDto.setGbiPicture(goods.getGbiPicture());
            tradeInfoDto.setUserId(vo.getTpiBuyerid());
            tradeInfoDto.setPrice(vo.getTpiPrice());
            tradeInfoDto.setNum(vo.getTpiNum());
            tradeInfoDto.setSuccessNum(vo.getTpiSucessnum());
            tradeInfoDto.setType(vo.getTpiType());
            tradeInfoDto.setTradeStatus(vo.getTpiStatus());
            tradeInfoDto.setCreatetime(vo.getTpiCreatetime());
            tradeInfoDto.setTradeTime(vo.getTpiBuytime());
            tradeInfoDto.setFinishtime(vo.getTpiFinishtime());
            respList.add(tradeInfoDto);
        }
        return respList;

    }

    private List<TradeInfoDto> buildSellTradeRespList( List<TradegoodsellVo> list) throws HzzBizException {

        List<TradeInfoDto> respList = new ArrayList<>();
        for (TradegoodsellVo vo : list) {
            TradeInfoDto tradeInfoDto = new TradeInfoDto();
            tradeInfoDto.setId(vo.getId());
            tradeInfoDto.setOrderId(vo.getTgsId());
            tradeInfoDto.setGbiId(vo.getGbiId());
            GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
            tradeInfoDto.setGbiName(goods.getGbiName());
            tradeInfoDto.setGbiPicture(goods.getGbiPicture());
            tradeInfoDto.setUserId(vo.getTgsSellerid());
            tradeInfoDto.setPrice(vo.getTgsPrice());
            tradeInfoDto.setNum(1);
            tradeInfoDto.setSuccessNum(1);
            if (vo.getTgsType() == 1) {
                tradeInfoDto.setType(3);
            } else if (vo.getTgsType() == 2) {
                tradeInfoDto.setType(4);
            }
            if (vo.getTgsStatus() == 0) {
                tradeInfoDto.setTradeStatus(1);
            } else if (vo.getTgsStatus() == 2) {
                tradeInfoDto.setTradeStatus(4);
            } else if (vo.getTgsStatus() == 3) {
                tradeInfoDto.setTradeStatus(2);
            } else if (vo.getTgsStatus() == 4) {
                tradeInfoDto.setTradeStatus(5);
            } else if (vo.getTgsStatus() == 1) {
                tradeInfoDto.setTradeStatus(6);
            }
            tradeInfoDto.setTradeStatus(vo.getTgsStatus());
            tradeInfoDto.setCreatetime(vo.getTgsCreatetime());
            tradeInfoDto.setTradeTime(vo.getTgsTradetime());
            tradeInfoDto.setFinishtime(vo.getTgsFinshitime());
            respList.add(tradeInfoDto);
        }
        return respList;

    }


}
