package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.dto.UserGoodsWithPickUpDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.CustomerpickupinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerpickupinfoVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/pickup")
public class PickupController {


    @Resource
    private CustomerpickupinfoService customerpickupinfoService;

    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;

    @ApiOperation(value="提货",notes="用户登录就可以")
    @PostMapping()
    @VerifyToken
    public BaseRspDto<String> pickup(@RequestParam @Digits(integer = 20,fraction = 0) long gbiid,
                                     @RequestParam @Digits(integer = 20,fraction = 0) long caiid,@RequestParam @Digits(integer = 20,fraction = 0)@Min(1) int num) {

        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            customerpickupinfoService.pickup(userInfo.getCbiId(),gbiid,num,caiid);
        }catch (HzzBizException e) {
            log.error("用户提货业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("用户提货系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @ApiOperation(value="提货进度列表",notes="用户登录就可以")
    @PostMapping(value = "/getPickupList")
    @VerifyToken
    public BaseRspDto<List<CustomerpickupinfoVo>> getPickupList(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                                @RequestParam @Digits(integer = 10,fraction = 0) Integer size){

        BaseRspDto<List<CustomerpickupinfoVo>> rsp = new BaseRspDto<List<CustomerpickupinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<CustomerpickupinfoVo> userGoodsDtoList = customerpickupinfoService.getCustomerpickupinfos(userInfo.getCbiId(), beg, size);
            rsp.setData(userGoodsDtoList);
       /* } catch (HzzBizException e) {
            log.error("获取用户现货商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());*/
        } catch (Exception e) {
            log.error("获取提货进度列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    @ApiOperation(value="提货明细",notes="用户登录就可以")
    @PostMapping(value = "/getPickupDetail")
    @VerifyToken
    public BaseRspDto<List<UserGoodsWithPickUpDto>> getPickupDetail(@RequestParam @Digits(integer = 10,fraction = 0) Integer gbiid){

        BaseRspDto<List<UserGoodsWithPickUpDto>> rsp = new BaseRspDto<List<UserGoodsWithPickUpDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<UserGoodsWithPickUpDto> userGoodsDtoList = customergoodsrelatedService.getCanPickUpOfGbi(userInfo.getCbiId(), gbiid);
            rsp.setData(userGoodsDtoList);
       /* } catch (HzzBizException e) {
            log.error("获取用户现货商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());*/
        } catch (Exception e) {
            log.error("获取提货明细系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
}
