package com.akfly.hzz.constant;

public  class CommonConstant {
    public static final String GOODSTYPE_ZC ="1";//1正常商品 2新手商品
    public static final String GOODSTYPE_XS ="2";

    public static final String LUNBO_SY ="1";//1首页轮播 2交易大厅轮播 3 通知广播 4 活动
    public static final String LUNBO_JY ="2";
    public static final String LUNBO_TZ ="3";
    public static final String LUNBO_HD ="4";


    // redis 的key定义
    public static final String MSG_CODE_LOGIN = "msg_code:login:";
    public static final String MSG_CODE_REGISTER = "msg_code:register:";
    public static final String MSG_CODE_FORGET_PSW = "msg_code:forget_psw:";
    public static final String USER_PREFIX = "user:";
    public static final String GOODS_PREFIX = "goods:";


    public static final String USER_INFO = "user_info";

    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 支付宝支付成功标识码
     */
    public static final String ALIPAY_SUCCESS_RESP_CODE ="success";

    /**
     * 返回给第三方的失败标识
     */
    public static final String RESPTO_BANK_FAIL_MSG ="fail";

    /**
     * 支付宝成功标识码
     */
    public static final String ALI_SUCCESS_RESP_CODE ="TRADE_SUCCESS";

    /**
     * 返回给第三方的成功标识
     */
    public static final String RESPTO_BANK_SUCCESS_MSG ="success";
}
