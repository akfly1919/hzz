package com.akfly.hzz.dto;

import lombok.Data;

/**
 * @title: RealNameReqDto
 * @projectName hzz
 * @description 实名请求dto
 * @author
 * @date 2021/1/23 9:44
 */

@Data
public class RealNameReqDto {

    private String name;

    private String identityCode;

    private String phoneNum;

    private String idFrontImgName;

    private String idBackImgName;
}
