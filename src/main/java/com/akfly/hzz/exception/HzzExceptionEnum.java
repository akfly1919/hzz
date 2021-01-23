package com.akfly.hzz.exception;


public enum HzzExceptionEnum {

    SUCCESS("0000", "成功"),
    FAILED("0001", "失败"),
    PROCESSING("0002", "处理中"),

    /* 系统异常定义以1开头 */
    NAME_OR_PSW_ERROR("1001", "用户名或者密码错误"),
    USER_NOTEXIST_ERROR("1002", "用户不存在"),
    MSG_CODE_INVALID("1003", "验证码无效"),
    PHONENUM_EXIST("1004", "该手机号已经注册过"),
    IMAGE_NOT_EXIST("1005", "图片不存在"),
    USER_NOT_REALNAME("1006", "用户未实名"),
    USER_NOT_LOGIN("1007", "用户未登录"),

    /* 业务异常定义以2-8开头，顺序往下排号 */
    PARAM_INVALID("2000", "参数不合法"),
    PSW_NOT_SAME("2000", "密码不一致"),

    /* 未知异常定义以9开头 */
    SYSTEM_ERROR("9000", "系统异常"),
    DB_ERROR("9001", "数据库异常"),
    UNKNOWN_ERROR("9999", "其他异常"),

    TEMP_ERROR("", "");////这个必须要留着

    private String errorCode;
    private String errorMsg;

    HzzExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static HzzExceptionEnum buildInterPayExceptionEnum(String errorCode, String errorMsg) {
        HzzExceptionEnum tempEnum = TEMP_ERROR;
        tempEnum.setErrorCode(errorCode);
        tempEnum.setErrorMsg(errorMsg);
        return tempEnum;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static HzzExceptionEnum getEnumByCodeValue(String typeCode) {
        HzzExceptionEnum[] outputTypeEnums = values();
        for (HzzExceptionEnum hzzExceptionEnum : outputTypeEnums) {
            String nowTypeCode = hzzExceptionEnum.getErrorCode();
            if (nowTypeCode.equals(typeCode)) return hzzExceptionEnum;
        }

        return null;
    }
}
