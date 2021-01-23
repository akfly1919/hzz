package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/")
public class LoginController {


	@RequestMapping(value = "/login")
	public String login(@VerifyToken CustomerbaseinfoVo vo, HttpServletRequest request, String phoneNum, String psw) {

		log.info("login测试拦截器 uerId={}", vo.getCbiId());
		if ("123".equals(phoneNum) && "456".equals(psw)) {
			request.getSession().setAttribute("phoneNum", phoneNum);
			log.info("phoneNum={}, sessionId={}, port={}", phoneNum, request.getSession().getId(), request.getServerPort());
			return "hello world!!" + phoneNum;
		} else if ("456".equals(phoneNum) && "456".equals(psw)) {
			request.getSession().setAttribute("phoneNum", phoneNum);
			log.info("phoneNum={}, sessionId={}, port={}", phoneNum, request.getSession().getId(), request.getServerPort());
			return "hello world" + phoneNum;
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/get")
	public String get(HttpServletRequest request, String phoneNum) {

		String currentPhoneNum = (String)(request.getSession().getAttribute("phoneNum"));
		log.info("phoneNum={}, sessionId={}, port={}", currentPhoneNum, request.getSession().getId(), request.getServerPort());
		return "hello world!!" + phoneNum;
	}


}
