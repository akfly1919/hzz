package com.akfly.hzz.dto;

import lombok.Data;

/**
 * @author
 * @title: MessageCodeDto
 * @projectName hzz
 * @description 短信验证码Dto
 * @date 2021/1/22 19:13
 */
@Data
public class MessageCodeDto {

    private String phoneNum;

    private String msgCode;
}
