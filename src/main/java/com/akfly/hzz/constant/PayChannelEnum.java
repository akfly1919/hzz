package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 支付状态枚举类
 * @author MLL
 * @date 2021/1/24 21:13
 */


public enum PayChannelEnum {

    WECHAT(0, "微信支付"),
    ALIPAY(1, "支付宝支付");

    private int status;

    private String desc;

    PayChannelEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
