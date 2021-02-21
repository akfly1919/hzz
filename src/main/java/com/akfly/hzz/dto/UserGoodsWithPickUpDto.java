package com.akfly.hzz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @title: UserGoodsWithPickUpDto
 * @projectName hzz
 * @description
 * @author
 * @date 2021/2/5 23:33
 */

@Data
public class UserGoodsWithPickUpDto extends UserGoodsDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime cgrBuytime;

    private LocalDateTime cgrSelltime;

    private LocalDateTime cgrForzentime;
}
