package com.akfly.hzz.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @title: UserGoodsWithPickUpDto
 * @projectName hzz
 * @description
 * @author MLL
 * @date 2021/2/5 23:33
 */

@Data
public class UserGoodsWithPickUpDto extends UserGoodsDto {

    private LocalDateTime cgrBuytime;

    private LocalDateTime cgrSelltime;

    private LocalDateTime cgrForzentime;
}
