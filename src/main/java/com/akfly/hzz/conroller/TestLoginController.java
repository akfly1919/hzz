package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.LoggedIn;
import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/")
public class TestLoginController {


	@ApiOperation(value="自测使用 - 忽略",notes="忽略")

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	@VerifyToken
	public String login(@LoggedIn CustomerbaseinfoVo vo, HttpServletRequest request, String phoneNum, String psw) {

		log.info("login测试拦截器 uerId={}", JsonUtils.toJson(request.getAttribute(CommonConstant.USER_INFO)));
		log.info("login测试拦截器 vo={}", JsonUtils.toJson(vo));
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

	@ApiOperation(value="自测使用 - 忽略",notes="忽略")
	@RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
	public String get(HttpServletRequest request, String phoneNum) {

		String currentPhoneNum = (String)(request.getSession().getAttribute("phoneNum"));
		log.info("phoneNum={}, sessionId={}, port={}", currentPhoneNum, request.getSession().getId(), request.getServerPort());
		return "hello world!!" + phoneNum;
	}


}
