package com.akfly.hzz.dto;

import lombok.Data;

/**
 * @title: RealNameReqDto
 * @projectName hzz
 * @description 实名请求dto
 * @author MLL
 * @date 2021/1/23 9:44
 */

@Data
public class RealNameRspDto {

    private String name;

    private String identityCode;

    private String phoneNum;

    /**
     * 是否实名 0 未实名 1 实名
     */
    private int isRealName;

    /**
     * 身份证正面照地址
     */
    private String idCardFront;

    /**
     * 身份证反照地址
     */
    private String idCardBack;

}
