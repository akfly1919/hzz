package com.akfly.hzz.exception;




public class HzzBizException extends Exception {
    private String errorCode;
    private String errorMsg;


    public HzzBizException() {
        super();
    }

    public HzzBizException(String message) {
        super(message);
    }

    public HzzBizException(HzzExceptionEnum e) {
        super(e.getErrorMsg());
        this.errorCode = e.getErrorCode();
        this.errorMsg = e.getErrorMsg();
    }

    public HzzBizException(HzzExceptionEnum e, String message) {
        super(message);
        this.errorCode = e.getErrorCode();
        this.errorMsg = message;
    }

    public HzzBizException(String code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMsg = message;
    }

    public HzzBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public HzzBizException(Throwable cause) {
        super(cause);
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

}
