package com.akfly.hzz.dto;  /**
 * @title: HistoryTradeDto
 * @projectName hzz
 * @description 历史交易
 * @author MLL
 * @date 2021/2/28 12:02
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: HistoryTradeDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 12:02
 */
@Data
public class HistoryTradeDto {

    /**
     * 买入金额
     */
    private BigDecimal buyNum;

    /**
     * 卖出金额
     */
    private BigDecimal sellNum;

    /**
     * 提货金额
     */
    private BigDecimal pickupNum;

    /**
     * 特价买入金额
     */
    private BigDecimal specialBuyNum;
}
