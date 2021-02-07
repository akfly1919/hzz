package com.akfly.hzz.dto;

import lombok.Data;

/**
 * @author MLL
 * @title: TaskGoodsDto
 * @projectName hzz
 * @description
 * @date 2021/2/7 14:22
 */
@Data
public class TaskGoodsDto extends UserGoodsDto {

    private int buyNum;

    private int pickUpNum;
}
