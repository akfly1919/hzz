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
public class ForgetPswReqDto {

    private String msgCode;

    private String newPsw;

    private String phoneNum;

}
