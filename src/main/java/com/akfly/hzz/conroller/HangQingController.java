package com.akfly.hzz.conroller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/hq")
public class HangQingController {


	@GetMapping(value = "/index")
	public String index() {
		return "hello world!!";
	}

}
