package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.PinGouInfoDtos;
import com.akfly.hzz.dto.TeamDetailsDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.CustomerbillrelatedService;
import com.akfly.hzz.service.CustomerrelationinfoService;
import com.akfly.hzz.service.PingouinfoService;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.akfly.hzz.vo.CustomerrelationinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.PingouinfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 用户关系表 前端控制器
 * </p>
 *
 * @author wangfei
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/hzz/customerRelation")
@Slf4j
public class CustomerrelationinfoController {

    @Resource
    private CustomerrelationinfoService customerrelationinfoService;

    @Resource
    private CustomerbillrelatedService customerbillrelatedService;

    @Resource
    private CustomerbaseinfoService customerbaseinfoService;

    @ApiOperation(value="团队详情",notes="用户登录就可以")
    @ApiImplicitParams({
            @ApiImplicitParam(name="beg",value="开始页数(从0开始取值)",required=true),
            @ApiImplicitParam(name="size",value="每页显示多少条数据",required=true),
            @ApiImplicitParam(name="level",value="标识(1: 1级伙伴 2: 2级伙伴)",required=true)
    })
    @PostMapping(value = "/getTeamDetails")
    @VerifyToken
    public BaseRspDto<List<TeamDetailsDto>> getTeamDetails(@RequestParam @Digits(integer = 10,fraction = 0) Integer beg,
                                                           @RequestParam @Digits(integer = 10,fraction = 0) Integer size,
                                                           @NotNull @RequestParam Integer level){
        BaseRspDto<List<TeamDetailsDto>> rsp = new BaseRspDto<List<TeamDetailsDto>>();
        try {
            CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
            List<CustomerrelationinfoVo> list = customerrelationinfoService.getCustomerRelations(userInfo.getCbiId(), level, size, beg);
            List<TeamDetailsDto> dtos = new ArrayList<TeamDetailsDto>();
            for (CustomerrelationinfoVo vo : list) {
                TeamDetailsDto dto = new TeamDetailsDto();
                dto.setCustomerrelationinfoVo(vo);
                CustomerbaseinfoVo pinGouUserInfo = customerbaseinfoService.getUserInfoById(vo.getCriMember());
                dto.setCbiUsername(nameSensitive(pinGouUserInfo.getCbiName(), pinGouUserInfo.getCbiUsername()));
                dto.setPhoneNum(pinGouUserInfo.getCbiPhonenum());
                BigDecimal rechargeAmount = customerbillrelatedService.sumAmount(Long.parseLong(vo.getCriMember()), 1);
                dto.setRechargeAmount(rechargeAmount);
                dtos.add(dto);
            }
            rsp.setData(dtos);
        }  catch (Exception e) {
            log.error("团队详情系统异常", e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }

    private String nameSensitive(String userName, String loginName) {

        if (StringUtils.isBlank(userName)) {
            if (loginName.length() <= 2) {
                return loginName.charAt(0) + "*";
            } else {
                return loginName.charAt(0) + "*" + loginName.charAt(loginName.length() - 1);
            }
        } else {
            if (loginName.length() <= 2) {
                return userName.charAt(0) + "*";
            } else {
                return userName.charAt(0) + "*" + userName.charAt(userName.length() - 1);
            }
        }

    }
}

