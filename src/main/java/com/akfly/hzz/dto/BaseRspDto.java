package com.akfly.hzz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: BaseRspDto
 * @projectName hzz
 * @description 响应消息顶级类，定义报文格式
 * @author MLL
 * @date 2021/1/20 22:18
 */
@Data
public class BaseRspDto<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

}
