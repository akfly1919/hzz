package com.akfly.hzz.conroller;


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


	@GetMapping(value = "/index")
	public String index() {
		return "hello world!!";
	}

}
