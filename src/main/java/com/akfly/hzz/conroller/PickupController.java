package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.PickUpEnum;
import com.akfly.hzz.constant.StockEnum;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
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
    CustomerpickupinfoService customerpickupinfoService;
    @ApiOperation(value="提货",notes="用户登录就可以")
    @PostMapping()
    @VerifyToken
    public BaseRspDto<String> pickup(@RequestParam @Digits(integer = 20,fraction = 0) long gbiid,@RequestParam @Digits(integer = 20,fraction = 0) long caiid,@RequestParam @Digits(integer = 20,fraction = 0)@Min(1) int num) {

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
    @ApiOperation(value="获取用户提货商品信息(不分页)",notes="用户登录就可以")
    @PostMapping(value = "/getPickupList")
    @VerifyToken
    public BaseRspDto<List<CustomerpickupinfoVo>> getPickupList(){

        BaseRspDto<List<CustomerpickupinfoVo>> rsp = new BaseRspDto<List<CustomerpickupinfoVo>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<CustomerpickupinfoVo> userGoodsDtoList = customerpickupinfoService.listCustomerpickupinfo(userInfo.getCbiId());
            rsp.setData(userGoodsDtoList);
       /* } catch (HzzBizException e) {
            log.error("获取用户现货商品信息业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());*/
        } catch (Exception e) {
            log.error("获取用户提货商品信息系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
}
