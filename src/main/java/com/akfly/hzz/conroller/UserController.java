package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.LoggedIn;
import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.dto.MessageCodeDto;
import com.akfly.hzz.dto.RealNameReqDto;
import com.akfly.hzz.dto.RealNameRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.CustomeridcardinfoService;
import com.akfly.hzz.util.*;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;


@Slf4j
@RestController
@RequestMapping(value = "/hzz/user")
public class UserController {

	private static final String MD5_KEY = "Jg7ZjmnxE8c7RCXy";

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private CustomerbaseinfoService customerbaseinfoService;

	@Resource
	private CustomeridcardinfoService customeridcardinfoService;


	@RequestMapping(value = "/login")
	public BaseRspDto login(HttpServletResponse response, String phoneNum, String psw, String pswType) {

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
			response.setHeader("token", TokenUtils.getToken(customerbaseinfoVo.getCbiId().toString(), customerbaseinfoVo.getCbiPassword()));
			customerbaseinfoVo.setCbiPassword("");  // 去掉密码
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
		return rsp;
	}

	@RequestMapping(value = "/register")
	public BaseRspDto register(HttpServletResponse response, String phoneNum, String psw, String repeatPsw) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(psw) || StringUtils.isBlank(repeatPsw)) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			if (!repeatPsw.equals(psw)) {
				throw new HzzBizException(HzzExceptionEnum.PSW_NOT_SAME);
			}
			CustomerbaseinfoVo existUser = customerbaseinfoService.getUserInfo(phoneNum);
			if (existUser == null) {
				customerbaseinfoService.userRegister(phoneNum, psw);
				CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoService.getUserInfo(phoneNum);
				response.setHeader("token", TokenUtils.getToken(customerbaseinfoVo.getCbiId().toString(), customerbaseinfoVo.getCbiPassword()));
				customerbaseinfoVo.setCbiPassword(null);  // 去掉密码
				//customerbaseinfoVo.setCbiCreatetime(DateHandlerUtil.getDateTimeFromDate(customerbaseinfoVo.getCbiCreatetime(), "yyyy-MM-dd HH:mm:ss"));
				rsp.setData(customerbaseinfoVo);
			} else {
				throw new HzzBizException(HzzExceptionEnum.PHONENUM_EXIST);
			}
		} catch (HzzBizException e) {
			log.error("用户注册业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("用户注册系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		String result = JsonUtils.toJson(rsp);
		log.info("用户注册返回信息 result={}", result);
		return rsp;
	}

	@RequestMapping(value = "/logout")
	@VerifyToken
	public BaseRspDto logout(HttpServletResponse response, String token) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			if (StringUtils.isBlank(token)) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			redisUtils.del("token"); // 清除缓存
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
		log.info("用户登出返回信息 result={}", result);
		return rsp;
	}


	@RequestMapping(value = "/sendMsgCode")
	public BaseRspDto sendMsgCode(String phoneNum) {

		String code = RandomGenUtils.getRandomNumberInRange(100000, 999999)+"";
		log.info("sendMsgCode phoneName:{},code:{}", phoneNum, code);
		redisUtils.set(CommonConstant.MSG_CODE_PREFIX + phoneNum, code, 300); // 有效期5分钟
		//return ""+SmsUtils.smsSend(phoneName,code);
		BaseRspDto<MessageCodeDto> rsp = new BaseRspDto<MessageCodeDto>();
		MessageCodeDto messageCodeDto = new MessageCodeDto();
		messageCodeDto.setPhoneNum(phoneNum);
		messageCodeDto.setMsgCode(code);
		rsp.setData(messageCodeDto);
		return rsp;
	}


	@PostMapping(value = "/realName")
	@ResponseBody
	@VerifyToken
	public BaseRspDto realName(@RequestBody RealNameReqDto realNameReqDto, @LoggedIn CustomerbaseinfoVo userInfo) {

		log.info("realName realNameReqDto:{}}", JsonUtils.toJson(realNameReqDto));
		BaseRspDto rsp = new BaseRspDto();
		try {
			if (StringUtils.isBlank(realNameReqDto.getName()) || StringUtils.isBlank(realNameReqDto.getIdentityCode()) ||
					StringUtils.isBlank(realNameReqDto.getPhoneNum())) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
			vo.setCbiId(userInfo.getCbiId());
			vo.setCbiName(realNameReqDto.getName());
			vo.setCbiIdcard(realNameReqDto.getIdentityCode());
			// TODO 需要放到一个事务
			customerbaseinfoService.updateUserInfo(vo);
			customeridcardinfoService.saveCardInfo(userInfo.getCbiId(), realNameReqDto.getIdFrontImgName(), realNameReqDto.getIdBackImgName());
		} catch (HzzBizException e) {
			log.error("用户实名认证业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("用户实名认证系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}

	@PostMapping(value = "/getRealName")
	@ResponseBody
	public BaseRspDto getRealName() {

		BaseRspDto<RealNameRspDto> rsp = new BaseRspDto<RealNameRspDto>();
		try {
			CustomerbaseinfoVo vo = customerbaseinfoService.getUserInfoById("");
			RealNameRspDto rnDto = new RealNameRspDto();
			rnDto.setName(vo.getCbiName());
			rnDto.setIdentityCode(vo.getCbiIdcard());
			rnDto.setPhoneNum(vo.getCbiPhonenum());
			rsp.setData(rnDto);
		} catch (HzzBizException e) {
			log.error("用户实名认证业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("用户实名认证系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}


	private String generateUserToken(String phoneNum) {

		return EncryDecryUtils.md5(phoneNum + MD5_KEY);
	}

}
