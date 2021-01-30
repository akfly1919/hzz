package com.akfly.hzz.conroller;


import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.dto.*;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.facade.IDFacade;
import com.akfly.hzz.interceptor.AuthInterceptor;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.service.CustomeridcardinfoService;
import com.akfly.hzz.util.*;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.applet.resources.MsgAppletViewer_zh_TW;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


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
	@Resource
	private IDFacade idFacade;

	@ApiOperation(value="用户登录",notes="同时支持手机验证码或者密码登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name="phoneNum",value="手机号",required=true),
			@ApiImplicitParam(name="psw",value="密码或者验证码",required=true),
			@ApiImplicitParam(name="pswType",value="密码类型(0: 代表密码登录 1: 代表手机验证码登录)",required=true)
	})
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public LoginRspDto login(HttpServletResponse response, String phoneNum, String psw, String pswType) {

		LoginRspDto<CustomerbaseinfoVo> rsp = new LoginRspDto<CustomerbaseinfoVo>();
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
			//response.setHeader("token", TokenUtils.getToken(customerbaseinfoVo.getCbiId().toString(), customerbaseinfoVo.getCbiPassword()));
			rsp.setToken(TokenUtils.getToken(customerbaseinfoVo.getCbiId().toString(), customerbaseinfoVo.getCbiPassword()));
			customerbaseinfoVo.setCbiPassword(null);  // 去掉密码
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

	@ApiOperation(value="用户注册",notes="密码注册")
	@ApiImplicitParams({
			@ApiImplicitParam(name="phoneNum",value="手机号",required=true),
			@ApiImplicitParam(name="psw",value="密码",required=true),
			@ApiImplicitParam(name="msgCode",value="短信验证码",required=true),
			@ApiImplicitParam(name="invitationCode",value="邀请码",required=false)
	})
	@RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
	public BaseRspDto register(HttpServletResponse response, String phoneNum, String psw, String msgCode, String invitationCode) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(psw) || StringUtils.isBlank(msgCode)) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			//if (!repeatPsw.equals(psw)) {
			//	throw new HzzBizException(HzzExceptionEnum.PSW_NOT_SAME);
			//}
			String key = CommonConstant.MSG_CODE_LOGIN + phoneNum;
			String redisCode = String.valueOf(redisUtils.get(key));
			if (!msgCode.equals(redisCode)) {
				throw new HzzBizException(HzzExceptionEnum.MSG_CODE_INVALID);
			} else {
				redisUtils.del(key); // 使用过之后就删除
			}
			CustomerbaseinfoVo existUser = customerbaseinfoService.getUserInfo(phoneNum);
			if (existUser == null) {
				customerbaseinfoService.userRegister(phoneNum, psw, invitationCode);
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

	@ApiOperation(value="退出登录",notes="用户退出登录")
	@PostMapping(value = "/logout")
	@VerifyToken
	public BaseRspDto logout(HttpServletResponse response) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			if (userInfo.getCbiId() == null) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			redisUtils.del(CommonConstant.USER_PREFIX + userInfo.getCbiId());
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

	@ApiOperation(value="获取用户信息",notes="要求用户登录")
	@RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
	@VerifyToken
	public BaseRspDto getUserInfo(HttpServletResponse response) {

		BaseRspDto<CustomerbaseinfoVo> rsp = new BaseRspDto<CustomerbaseinfoVo>();
		try {
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			if (userInfo.getCbiId() == null) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			CustomerbaseinfoVo customerbaseinfoVo = customerbaseinfoService.getUserInfoById(String.valueOf(userInfo.getCbiId()));
			rsp.setData(customerbaseinfoVo);
		} catch (HzzBizException e) {
			log.error("获取用户信息业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("获取用户信息系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}


	@ApiOperation(value="发送验证码",notes="目前没有真实发送，将验证码直接返回给前端")
	@ApiImplicitParams({
			@ApiImplicitParam(name="phoneNum",value="手机号",required=true),
			@ApiImplicitParam(name="msgType",value="短信码类型(0:登录 1:注册 2:找回密码)",required=true)
	})
	@RequestMapping(value = "/sendMsgCode", method = {RequestMethod.GET, RequestMethod.POST})
	public BaseRspDto sendMsgCode(String phoneNum, String msgType) {

		BaseRspDto<MessageCodeDto> rsp = new BaseRspDto<MessageCodeDto>();
		try {
			if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(msgType)) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}

			String code = RandomGenUtils.getRandomNumberInRange(100000, 999999)+"";
			log.info("sendMsgCode phoneNum:{},code:{}", phoneNum, code);
			String msg_prefix = "";
			if ("0".equals(msgType)) {
				msg_prefix = CommonConstant.MSG_CODE_LOGIN;
			} else if ("1".equals(msgType)) {
				msg_prefix = CommonConstant.MSG_CODE_REGISTER;
			} else if ("2".equals(msgType)) {
				msg_prefix = CommonConstant.MSG_CODE_FORGET_PSW;
			} else {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			redisUtils.set(msg_prefix + phoneNum, code, 300); // 有效期5分钟

			if (SmsUtils.smsSend(phoneNum,code)) {
				MessageCodeDto messageCodeDto = new MessageCodeDto();
				messageCodeDto.setPhoneNum(phoneNum);
				messageCodeDto.setMsgCode(code);
				rsp.setData(messageCodeDto);
			} else {
				rsp.setCode(HzzExceptionEnum.MESSAGE_CODE_FAILED.getErrorCode());
				rsp.setMsg(HzzExceptionEnum.MESSAGE_CODE_FAILED.getErrorMsg());
			}
		} catch (HzzBizException e) {
			log.error("sendMsgCode业务错误 msg={}", e.getErrorMsg(), e);
			rsp.setCode(e.getErrorCode());
			rsp.setMsg(e.getErrorMsg());
		} catch (Exception e) {
			log.error("sendMsgCode系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}


	@ApiOperation(value="用户实名认证",notes="目前暂未用OCR识别身份证")
	@ApiImplicitParams({
			@ApiImplicitParam(name="name",value="姓名",required=true),
			@ApiImplicitParam(name="identityCode",value="身份证号码",required=true),
			@ApiImplicitParam(name="phoneNum",value="手机号",required=true),
			@ApiImplicitParam(name="idBackImgName",value="身份证反面照片名称",required=true),
			@ApiImplicitParam(name="idFrontImgName",value="身份证正面照片名称",required=true)
	})
	@PostMapping(value = "/realName")
	@ResponseBody
	@VerifyToken
	public BaseRspDto realName(RealNameReqDto realNameReqDto) {

		log.info("realName realNameReqDto:{}", JsonUtils.toJson(realNameReqDto));
		BaseRspDto rsp = new BaseRspDto();
		try {
			if (StringUtils.isBlank(realNameReqDto.getName()) || StringUtils.isBlank(realNameReqDto.getIdentityCode()) ||
					StringUtils.isBlank(realNameReqDto.getPhoneNum())) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
			vo.setCbiId(userInfo.getCbiId());
			vo.setCbiName(realNameReqDto.getName());
			vo.setCbiIdcard(realNameReqDto.getIdentityCode());
			// TODO 需要放到一个事务
			idFacade.idRealAuth(realNameReqDto.getIdentityCode(),realNameReqDto.getName());
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

	@ApiOperation(value="用户实名认证查询",notes="要求登录就可以")
	@PostMapping(value = "/getRealName")
	@ResponseBody
	@VerifyToken
	public BaseRspDto getRealName() {

		BaseRspDto<RealNameRspDto> rsp = new BaseRspDto<RealNameRspDto>();
		try {
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			RealNameRspDto rnDto = new RealNameRspDto();
			rnDto.setName(userInfo.getCbiName());
			rnDto.setIdentityCode(userInfo.getCbiIdcard());
			rnDto.setPhoneNum(userInfo.getCbiPhonenum());
			rsp.setData(rnDto);
		} catch (Exception e) {
			log.error("用户实名认证系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}


	@ApiOperation(value="获取邀请码",notes="要求登录就可以")
	@PostMapping(value = "/getInvitationCode")
	@ResponseBody
	@VerifyToken
	public BaseRspDto getInvitationCode() {

		BaseRspDto<InvitationCode> rsp = new BaseRspDto<InvitationCode>();
		try {
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			long userId = userInfo.getCbiId();
			long code = (userId << 1) + 1;
			log.info("获取邀请码userId={}, code={}", userId, code);
			InvitationCode invitationCode = new InvitationCode();
			invitationCode.setCode(code);
			rsp.setData(invitationCode);
		} catch (Exception e) {
			log.error("获取邀请码系统异常", e);
			rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
			rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
		}
		return rsp;
	}


	@ApiOperation(value="用户修改登录密码",notes="要求登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name="oldPsw",value="原始密码",required=true),
			@ApiImplicitParam(name="newPsw",value="新密码",required=true),
			@ApiImplicitParam(name="repeatNewPsw",value="重复新密码",required=true)
	})
	@PostMapping(value = "/modifyPsw")
	@ResponseBody
	@VerifyToken
	public BaseRspDto modifyPsw(ModifyPswReqDto modifyPswReqDto) {

		log.info("modifyPsw modifyPswReqDto:{}", JsonUtils.toJson(modifyPswReqDto));
		BaseRspDto rsp = new BaseRspDto();
		try {
			if (StringUtils.isBlank(modifyPswReqDto.getOldPsw()) || StringUtils.isBlank(modifyPswReqDto.getNewPsw()) ||
					StringUtils.isBlank(modifyPswReqDto.getRepeatNewPsw()) || !modifyPswReqDto.getNewPsw().equals(modifyPswReqDto.getRepeatNewPsw())) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}
			CustomerbaseinfoVo userInfo = AuthInterceptor.getUserInfo();
			if (StringUtils.isBlank(userInfo.getCbiPassword())) {
				throw new HzzBizException(HzzExceptionEnum.USER_NOT_SET);
			}
			if (!userInfo.getCbiPassword().equals(modifyPswReqDto.getOldPsw())) {
				throw new HzzBizException(HzzExceptionEnum.USER_OLDPSW_ERROR);
			}
			CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
			vo.setCbiId(userInfo.getCbiId());
			vo.setCbiPassword(modifyPswReqDto.getNewPsw()); // TODO 密码后端需要加密
			customerbaseinfoService.updateUserInfo(vo);
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


	@ApiOperation(value="用户修改登录密码",notes="要求登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name="oldPsw",value="原始密码",required=true),
			@ApiImplicitParam(name="newPsw",value="新密码",required=true),
			@ApiImplicitParam(name="repeatNewPsw",value="重复新密码",required=true)
	})
	@PostMapping(value = "/forgetPsw")
	@ResponseBody
	public BaseRspDto forgetPsw(ForgetPswReqDto forgetPswReqDto) {

		log.info("forgetPsw modifyPswReqDto:{}", JsonUtils.toJson(forgetPswReqDto));
		BaseRspDto rsp = new BaseRspDto();
		try {
			if (StringUtils.isBlank(forgetPswReqDto.getNewPsw()) || StringUtils.isBlank(forgetPswReqDto.getPhoneNum()) ||
					StringUtils.isBlank(forgetPswReqDto.getMsgCode())) {
				throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
			}

			CustomerbaseinfoVo existUser = customerbaseinfoService.getUserInfo(forgetPswReqDto.getPhoneNum());

			if (existUser == null) {
				throw new HzzBizException(HzzExceptionEnum.PHONENUM_NOT_REGISTER);
			}
			String key = CommonConstant.MSG_CODE_LOGIN + forgetPswReqDto.getPhoneNum();
			String redisCode = String.valueOf(redisUtils.get(key));
			if (!forgetPswReqDto.getMsgCode().equals(redisCode)) {
				throw new HzzBizException(HzzExceptionEnum.MSG_CODE_INVALID);
			} else {
				redisUtils.del(key); // 使用过之后就删除
			}
			CustomerbaseinfoVo vo = new CustomerbaseinfoVo();
			vo.setCbiId(existUser.getCbiId());
			vo.setCbiPassword(forgetPswReqDto.getNewPsw()); // TODO 密码后端需要加密
			customerbaseinfoService.updateUserInfo(vo);
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
