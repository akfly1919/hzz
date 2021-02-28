package com.akfly.hzz.dto;  /**
 * @title: HistoryTradeDto
 * @projectName hzz
 * @description 团队和交易DTO
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
public class TeamAndTradeDto {

    private MyTeamDto myTeamDto;

    private HistoryTradeDto historyTradeDto;
}
