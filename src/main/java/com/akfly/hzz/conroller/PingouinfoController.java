package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.*;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 拼购奖励 前端控制器
 * </p>
 *
 * @author wangfei
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/hzz/pingou")
@Slf4j
public class PingouinfoController {

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

    @Resource
    private PingouinfoService pingouinfoService;

    @Resource
    private CustomerbaseinfoService customerbaseinfoService;

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private CustomerpickupinfoService customerpickupinfoService;

    @Resource
    private CustomerrelationinfoService customerrelationinfoService;

    @Resource
    private TradeorderinfoService tradeorderinfoService;

    @ApiOperation(value="获取商品值/佣金明细数据",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="开始页数(从0开始取值)",required=true),
            @ApiImplicitParam(name="size",value="每页显示多少条数据",required=true),
            @ApiImplicitParam(name="flag",value="标识(0:商品值明细 1:佣金明细)",required=true)
    })
    @PostMapping(value = "/getSumDetails")
    @VerifyToken
    public BaseRspDto<List<CustomerbillrelatedVo>> getSumDetails(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                                   @RequestParam @Digits(integer = 10,fraction = 0) Integer size,
                                                                   @NotNull @RequestParam Integer flag){
        BaseRspDto<List<CustomerbillrelatedVo>> rsp = new BaseRspDto<List<CustomerbillrelatedVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            int cbrClass = 10;
            if (flag == 1) {
                cbrClass = 11;
            }
            List<CustomerbillrelatedVo> list = customerbillrelatedService.getCustomerbillById(userInfo.getCbiId(), size, beg, cbrClass);
            rsp.setData(list);
        }  catch (Exception e) {
            log.error("获取商品值/佣金明细数据系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    @ApiOperation(value="拼购详情数据",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="开始页数(从0开始取值)",required=true),
            @ApiImplicitParam(name="size",value="每页显示多少条数据",required=true),
            @ApiImplicitParam(name="status",value="标识(0:全部 1: 拼购中 2: 已完成)",required=true)
    })
    @PostMapping(value = "/getPinGouDetails")
    @VerifyToken
    public BaseRspDto<List<PinGouInfoDtos>> getPinGouDetails(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                                 @RequestParam @Digits(integer = 10,fraction = 0) Integer size,
                                                                 @NotNull @RequestParam Integer status){
        BaseRspDto<List<PinGouInfoDtos>> rsp = new BaseRspDto<List<PinGouInfoDtos>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<PingouinfoVo> list = pingouinfoService.getPinGouInfos(userInfo.getCbiId(), status, size, beg);
            List<PinGouInfoDtos> dtos = new ArrayList<>();
            for (PingouinfoVo vo : list) {
                PinGouInfoDtos dto = new PinGouInfoDtos();
                dto.setPingouinfoVo(vo);
                CustomerbaseinfoVo pinGouUserInfo = customerbaseinfoService.getUserInfoById(String.valueOf(vo.getCbiId()));
                dto.setCbiUsername(pinGouUserInfo.getCbiUsername());
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                dto.setGbiName(goods.getGbiName());
                dto.setGbiPicture(goods.getGbiPicture());
                dtos.add(dto);
            }
            rsp.setData(dtos);
        }  catch (Exception e) {
            log.error("拼购详情数据数据系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="培养详情数据",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="开始页数(从0开始取值)",required=true),
            @ApiImplicitParam(name="size",value="每页显示多少条数据",required=true),
            @ApiImplicitParam(name="type",value="标识(1: 1级奖励 2: 2级奖励)",required=true)
    })
    @PostMapping(value = "/getPeiYangDetails")
    @VerifyToken
    public BaseRspDto<List<PinGouInfoDtos>> getPeiYangDetails(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                             @RequestParam @Digits(integer = 10,fraction = 0) Integer size,
                                                             @NotNull @RequestParam Integer type){
        BaseRspDto<List<PinGouInfoDtos>> rsp = new BaseRspDto<List<PinGouInfoDtos>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<PingouinfoVo> list = pingouinfoService.getPeiYangInfos(userInfo.getCbiId(), type, size, beg);
            List<PinGouInfoDtos> dtos = new ArrayList<>();
            for (PingouinfoVo vo : list) {
                PinGouInfoDtos dto = new PinGouInfoDtos();
                dto.setPingouinfoVo(vo);
                CustomerbaseinfoVo pinGouUserInfo = customerbaseinfoService.getUserInfoById(String.valueOf(vo.getCbiId()));
                dto.setCbiUsername(pinGouUserInfo.getCbiUsername());
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                dto.setGbiName(goods.getGbiName());
                dto.setGbiPicture(goods.getGbiPicture());
                dtos.add(dto);
            }
            rsp.setData(dtos);
        }  catch (Exception e) {
            log.error("拼购详情数据数据系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="拼购首页",notes="用户登录就可以")
    @PostMapping(value = "/getPinGouFirstPage")
    @VerifyToken
    public BaseRspDto<PinGouFirstPageRspDto> getPinGouFirstPage(){

        BaseRspDto<PinGouFirstPageRspDto> rsp = new BaseRspDto<PinGouFirstPageRspDto>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            long userId = userInfo.getCbiId();
            List<PinGouSumDto> pinGouSumDtos = pingouinfoService.getPinGouSumByGbiId(userId);
            List<PeiYangSumDto> peiYangSumDtos = pingouinfoService.getPeiYangSumByGbiId(userId);
            TeamAndTradeDto teamAndTradeDto = pingouinfoService.getMyTeamDto(userId);
            MyTeamDto myTeamDto = teamAndTradeDto.getMyTeamDto();
            PinGouFirstPageRspDto resp = new PinGouFirstPageRspDto();
            BigDecimal goodsAmount = customerbillrelatedService.sumAmount(userId, 10);
            BigDecimal commissionAmount = customerbillrelatedService.sumAmount(userId, 11);
            resp.setGoodsAmount(goodsAmount);
            resp.setCommissionAmount(commissionAmount);
            resp.setPeiYangSumDtos(peiYangSumDtos);
            resp.setPinGouSumDtos(pinGouSumDtos);
            resp.setMyTeamDto(myTeamDto);
            resp.setHistoryTradeDto(teamAndTradeDto.getHistoryTradeDto());
            rsp.setData(resp);
        //} catch (HzzBizException e) {
        //    log.error("拼购首页查询信息业务异常", e);
        //    rsp.setCode(e.getErrorCode());
        //    rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("拼购首页查询信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


    @ApiOperation(value="历史交易明细",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="标识(1: 1级伙伴 2: 2级伙伴)",required=true)
    })
    @PostMapping(value = "/getHistoryTradeDetails")
    @VerifyToken
    public BaseRspDto<List<PinGouTradeDetailDto>> getHistoryTradeDetails(@NotNull @RequestParam Integer type){

        BaseRspDto<List<PinGouTradeDetailDto>> rsp = new BaseRspDto<List<PinGouTradeDetailDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            long userId = userInfo.getCbiId();
            List<CustomerrelationinfoVo> list = customerrelationinfoService.getCustomerrelationinfoVo(userId, type);
            List<PinGouTradeDetailDto> dtos = new ArrayList<>();
            for (CustomerrelationinfoVo vo : list) {
                HistoryTradeDto currentDto = tradeorderinfoService.getSumAmount(Long.valueOf(vo.getCriMember()), true);
                HistoryTradeDto historyDto = tradeorderinfoService.getSumAmount(Long.valueOf(vo.getCriMember()), false);
                PinGouTradeDetailDto dto = new PinGouTradeDetailDto();
                CustomerbaseinfoVo user = customerbaseinfoService.getUserInfoById(vo.getCriMember());
                dto.setCbiUsername(user.getCbiUsername());
                dto.setHistoryDay(historyDto);
                dto.setCurrentDay(currentDto);
                dtos.add(dto);
            }
            rsp.setData(dtos);
        } catch (HzzBizException e) {
            log.error("拼购首页查询信息业务异常", e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("拼购首页查询信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }


}

