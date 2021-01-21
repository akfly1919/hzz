package com.akfly.hzz.conroller;


import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.util.EncryDecryUtils;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.util.SmsUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/user")
public class UserController {

	private static final String MD5_KEY = "Jg7ZjmnxE8c7RCXy";

	@Autowired
	private CustomerbaseinfoService customerbaseinfoService;

	@RequestMapping(value = "/login")
	public String login(String phoneNum, String psw, String pswType) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(psw) || StringUtils.isBlank(pswType)) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			CustomerbaseinfoVo customerbaseinfoVo = null;
			// pswType 0:密码 1:验证码
			if ("0".equals(pswType)) {
				customerbaseinfoVo = customerbaseinfoService.userLoginByPsw(phoneNum, psw);
			} else if (("1".equals(pswType))) {
				customerbaseinfoVo = customerbaseinfoService.userLoginByCode(phoneNum, psw);
			} else {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			customerbaseinfoVo.setUserToken(generateUserToken(phoneNum));
			rsp.setData(customerbaseinfoVo);
		} catch (HzzBizException e) {
			log.error("用户登录业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("用户登录系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		String result = JsonUtils.toJson(rsp);
		log.info("用户登录返回信息 result={}", result);
		return JsonUtils.toJson(rsp);
	}

	@RequestMapping(value = "/yanzhenma")
	public String yanzhenma(String phoneName) {
		String code = RandomGenUtils.getRandomNumberInRange(100000, 999999)+"";
		//todo code写入缓存redis确认之后删除
		log.info("yanzhenma phoneName:{},code:{}",phoneName,code);
		return ""+SmsUtils.smsSend(phoneName,code);
	}


	private String generateUserToken(String phoneNum) {

		return EncryDecryUtils.md5(phoneNum + MD5_KEY);
	}

}
