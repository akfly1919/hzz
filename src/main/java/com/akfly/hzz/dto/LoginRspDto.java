package com.akfly.hzz.dto;  /**
 * @title: LoginRspDto
 * @projectName hzz
 * @description
 * @author
 * @date 2021/1/26 22:41
 */

import lombok.Data;


@Data
public class LoginRspDto<T> extends BaseRspDto<T> {

    private String token;
}
