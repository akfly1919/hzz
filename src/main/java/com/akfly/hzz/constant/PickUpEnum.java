package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 提货枚举类
 * @author MLL
 * @date 2021/1/24 21:13
 */


public enum PickUpEnum {

    UNPICK(0, "未提货"),
    PICK(1, "已提货");

    private int status;

    private String desc;

    PickUpEnum(int status, String desc) {
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
