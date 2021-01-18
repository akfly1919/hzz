package com.akfly.hzz.conroller;


import com.akfly.hzz.service.impl.CustomerbaseinfoServiceImpl;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/sy")
public class ShouYeController {
    @Autowired
    CustomerbaseinfoServiceImpl customerbaseinfoService;

    /**
     * 兑换商品本期不实现
     * 头图和活动区跳转，商品详情页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        LambdaQueryWrapper<CustomerbaseinfoVo> cw = Wrappers.lambdaQuery();
        List<CustomerbaseinfoVo> customerbaseinfoVoList = customerbaseinfoService.list(cw);
        log.info("wodetian:{}",customerbaseinfoVoList);
        return "hello world!!";
    }

}
