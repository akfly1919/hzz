package com.akfly.hzz.dto;

import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradegoodsellVo;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @title: SellTradeDetailDto
 * @projectName hzz
 * @description 交易明细DTO
 * @date 2021/2/24 13:44
 */
@Data
public class SellTradeDetailDto {

    private GoodsbaseinfoVo goodsBaseInfo;

    private List<TradegoodsellVo> tradeGoodSells;
}
