package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 支付状态枚举类
 * @author MLL
 * @date 2021/1/24 21:13
 */


public enum PayStatus {

    UN_PAY(0, "未付款"),
    PAYED(1, "已付款");

    private int status;

    private String desc;

    PayStatus(int status, String desc) {
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
