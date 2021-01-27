package com.akfly.hzz.constant;

/**
 * @author MLL
 * @title: CliDeviceTypeEnum
 * @projectName hzz
 * @description 客户端枚举类
 * @date 2021/1/27 15:30
 */
public enum CliDeviceTypeEnum {

    IOS((byte)0),
    ANDROID((byte)1),
    WAP((byte)2),
    UNKNOW((byte)3);

    private final byte value;

    private CliDeviceTypeEnum(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }
}
