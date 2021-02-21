package com.akfly.hzz.dto;

import lombok.Data;

/**
 * @author
 * @title: TaskGoodsDto
 * @projectName hzz
 * @description
 * @date 2021/2/7 14:22
 */
@Data
public class TaskGoodsDto extends UserGoodsDto {

    private int buyNum;

    private int pickUpNum;

    /**
     * 0 不够资格购买  1 够资格购买特价商品
     */
    private int canBuy;

    /**
     * 奖励任务个数
     */
    private int discountNumConfig;

    /**
     * 本次可购买个数
     */
    private int canBuyNum;

    /**
     * 买入个数配置
     */
    private int buyConfig;

    /**
     * 提货个数配置
     */
    private int pickUpConfig;

}
