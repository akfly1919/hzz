package com.akfly.hzz.conroller;

import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.service.ReporttradedateService;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.ReporttradedateVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
