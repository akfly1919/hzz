package com.akfly.hzz.dto;

import lombok.Data;

@Data
public class UserGoodsDto {


    private Long gbiId;

    /**
     * 产品名称
     */
    private String gbiName;

    /**
     * 1酒类
     */
    private Integer gbiCid;

    /**
     * 商品id
     */
    private String gbiTitle;

    /**
     * 商品id
     */
    private String gbiDesc;

    /**
     * 特惠价格
     */
    private Double gbiDiscountprice;

    /**
     * 产品价格
     */
    private Double gbiPrice;

    /**
     * 发行总数量
     */
    private Integer gbiCount;

    /**
     * 1正常商品 2新手商品
     */
    private Integer gbiType;

    /**
     * 0未上架 1上架
     */
    private Integer gbiSaleable;

    /**
     * 每人限购
     */
    private Integer gbiLimitperson;

    /**
     * 每日限购
     */
    private Integer gbiLimitday;

    /**
     * 起卖份数
     */
    private Integer gbiLimitsalenum;

    /**
     * 价格下限
     */
    private Double gbiPricefloor;

    /**
     * 价格上限
     */
    private Double gbiPriceceiling;

    /**
     * 商品买入服务费比例
     */
    private Double gbiBuyservicerate;

    /**
     * 商品卖出服务比率
     */
    private Double gbiSellservicerate;

    /**
     * 排序
     */
    private Integer gbiSort;

    /**
     * 商品主图
     */
    private String gbiPicture;

    /**
     * 提货最低限制
     */
    private Integer gbiLimitpickup;

    /**
     * 用户id
     */
    private Long cbiid;

    /**
     * 库存
     */
    private Long stock;

}
