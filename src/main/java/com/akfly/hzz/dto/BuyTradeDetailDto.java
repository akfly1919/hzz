package com.akfly.hzz.dto;

import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.akfly.hzz.vo.TradepredictinfoVo;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @title: BuyTradeDetailDto
 * @projectName hzz
 * @description 交易明细DTO
 * @date 2021/2/24 13:44
 */
@Data
public class BuyTradeDetailDto {

    private GoodsbaseinfoVo goodsBaseInfo;

    private List<TradepredictinfoVo> tradePredictInfos;
}
