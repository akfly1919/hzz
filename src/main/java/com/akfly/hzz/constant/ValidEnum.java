package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 支付状态枚举类
 * @author
 * @date 2021/1/24 21:13
 */


public enum ValidEnum {

    UNVALID(0, "无效"),
    VALID(1, "有效");

    private int status;

    private String desc;

    ValidEnum(int status, String desc) {
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
