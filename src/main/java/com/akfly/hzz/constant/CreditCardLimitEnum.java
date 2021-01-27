package com.akfly.hzz.constant;

/**
 * 是否支持信用卡
 */
public enum CreditCardLimitEnum {
                                 CREDIT((byte) 0, "credit", "支持"), NO_CREDIT((byte) 1, "no_credit", "不支持");

    private Byte   code;
    private String name;
    private String desc;

    CreditCardLimitEnum(Byte code, String name, String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static CreditCardLimitEnum getCreditCardLimit(Byte code) {
        for (CreditCardLimitEnum outputType : CreditCardLimitEnum.values()) {
            if (outputType.getCode().equals(code)) {
                return outputType;
            }
        }
        return null;
    }

    public static CreditCardLimitEnum getCreditCardLimit(String name) {
        for (CreditCardLimitEnum outputType : CreditCardLimitEnum.values()) {
            if (outputType.getName().equals(name)) {
                return outputType;
            }
        }
        return null;
    }

}
