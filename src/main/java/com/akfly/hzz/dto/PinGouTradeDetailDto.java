package com.akfly.hzz.dto;  /**
 * @title: PinGouTradeDetailDto
 * @projectName hzz
 * @description 拼购首页历史交易详情
 * @author MLL
 * @date 2021/2/28 22:59
 */

import lombok.Data;

/**
 * @ClassName: PinGouTradeDetailDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 22:59
 */
@Data
public class PinGouTradeDetailDto {

    private Long cbiId;

    private String cbiUsername;

    private HistoryTradeDto currentDay;

    private HistoryTradeDto historyDay;
}
