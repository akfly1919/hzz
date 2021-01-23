package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.LoggedIn;
import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomeraddressinfoService;
import com.akfly.hzz.vo.CustomeraddressinfoVo;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/addressinfo")
public class AddressInfoController {

    @Autowired
    CustomeraddressinfoService customeraddressinfoService;

    @ApiOperation(value="获取收货地址",notes="用户登录就可以")
    @GetMapping
    @VerifyToken
    public BaseRspDto<List<CustomeraddressinfoVo>> getCustomerAddressInfo(@LoggedIn CustomerbaseinfoVo vo){
        BaseRspDto<List<CustomeraddressinfoVo>> rsp = new BaseRspDto<List<CustomeraddressinfoVo>>();
        try {
            List<CustomeraddressinfoVo> list = customeraddressinfoService.getAddressInfoById(vo.getCbiId());
            rsp.setData(list);
        } catch (HzzBizException e) {
            log.error("查询地址列表业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("查询地址列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @PutMapping
    @VerifyToken
    public  BaseRspDto<String> createCustomerAddressInfo(@LoggedIn CustomerbaseinfoVo vo,@Validated CustomeraddressinfoVo customeraddressinfoVo){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            customeraddressinfoVo.setCbiId(vo.getCbiId());
            customeraddressinfoVo.setCaiUpdatetime(LocalDateTime.now());
            customeraddressinfoVo.setCaiValid(1);
            customeraddressinfoVo.setCaiSort(1);
            customeraddressinfoService.createAddressInfo(customeraddressinfoVo);
        } catch (HzzBizException e) {
            log.error("创建地址列表业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("创建地址列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
    @PostMapping
    @VerifyToken
    public  BaseRspDto<String> updateCustomerAddressInfo(@LoggedIn CustomerbaseinfoVo vo,@Validated CustomeraddressinfoVo customeraddressinfoVo){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        try {
            customeraddressinfoVo.setCbiId(vo.getCbiId());
            customeraddressinfoVo.setCaiUpdatetime(LocalDateTime.now());
            customeraddressinfoVo.setCaiValid(1);
            customeraddressinfoVo.setCaiSort(1);
            customeraddressinfoService.updateAddressInfo(customeraddressinfoVo);
        } catch (HzzBizException e) {
            log.error("创建地址列表业务错误 msg={}", e.getErrorMsg(), e);
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("创建地址列表系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

}
