package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 提货枚举类
 * @author
 * @date 2021/1/24 21:13
 */


public enum InOrOutTypeEnum {

    IN(1, "收入"),
    OUT(2, "支出");

    private int status;

    private String desc;

    InOrOutTypeEnum(int status, String desc) {
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
