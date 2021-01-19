package com.akfly.hzz.conroller;


import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.util.SmsUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/user")
public class UserController {


	@GetMapping(value = "/login")
	public String login() {
		return "hello world!!";
	}

	@GetMapping(value = "/yanzhenma")
	public String yanzhenma(String phoneName) {
		String code = RandomGenUtils.getRandomNumberInRange(100000, 999999)+"";
		//todo code写入缓存redis确认之后删除
		log.info("yanzhenma phoneName:{},code:{}",phoneName,code);
		return ""+SmsUtils.smsSend(phoneName,code);
	}

}
