package com.akfly.hzz.constant;  /**
 * @title: CbrClassEnum
 * @projectName hzz
 * @description 账户流水表的类型枚举
 * @author MLL
 * @date 2021/1/24 21:13
 */


public enum CbrClassEnum {

    RECHARGE(1, "充值收入"),
    GOODSIN(2, "商品收入"),  // 卖货收入
    WITHDRAWOUT(3, "提现支出"),
    SELLFEE(4, "卖货服务费"),
    BUYFEE(5, "买货服务费"),
    WITHDRAWFEE(6, "提现手续费"),
    GOOGSOUT(7, "买货支出")  // 买货支出
    ;

    private int status;

    private String desc;

    CbrClassEnum(int status, String desc) {
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
