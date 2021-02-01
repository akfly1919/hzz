package com.akfly.hzz.conroller;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.ReporttradedateVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/hzz/statistics")
@Validated
public class StatisticsController {
    @Autowired
    ReporttradedateService seporttradedateService;

    @ApiOperation(value="交易行情",notes="交易行情查询")
    @PostMapping
    BaseRspDto<List<ReporttradedateVo>> listReporttradedateStatistics(@RequestParam @NotNull Long gbid, @RequestParam @Pattern(regexp = "YEAR|MONTH|DAY|WEEK|HOUR") String queryType){
        BaseRspDto<List<ReporttradedateVo>> rsp = new BaseRspDto<List<ReporttradedateVo>>();
        try {
            List<ReporttradedateVo> list = seporttradedateService.listReporttradedateStatistics(gbid.longValue(), queryType);
            rsp.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        return rsp;
    }
}
