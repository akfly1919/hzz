package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 库存枚举类
 * @author MLL
 * @date 2021/1/24 21:13
 */


public enum StockEnum {

    UNLOCKED(0, "未锁定"),
    LOCKED(1, "锁定"),
    FROZEN(2, "冻结"),
    XIANHUO(0, "现货")
    ;

    private int status;

    private String desc;

    StockEnum(int status, String desc) {
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
