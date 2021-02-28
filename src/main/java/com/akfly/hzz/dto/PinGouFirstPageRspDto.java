package com.akfly.hzz.dto;  /**
 * @title: PinGouFirstPageRspDto
 * @projectName hzz
 * @description  拼购首页响应dto
 * @author MLL
 * @date 2021/2/28 11:53
 */

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: PinGouFirstPageRspDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 11:53
 */
@Data
public class PinGouFirstPageRspDto {

    private BigDecimal goodsAmount;

    private BigDecimal commissionAmount;

    private List<PeiYangSumDto> peiYangSumDtos;

    private List<PinGouSumDto> pinGouSumDtos;

    private MyTeamDto myTeamDto;

    private HistoryTradeDto historyTradeDto;


}
