package com.akfly.hzz.constant;  /**
 * @title: PayStatus
 * @projectName hzz
 * @description 库存枚举类
 * @author
 * @date 2021/1/24 21:13
 */


public enum StockEnum {

    UNLOCKED(0, "未锁定"),  // 现货库存
    LOCKED(2, "锁定"),
    FROZEN(1, "冻结"),
    XIANHUO(3, "现货"), // 可提货库存
    ALL(4, "总库存")
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
