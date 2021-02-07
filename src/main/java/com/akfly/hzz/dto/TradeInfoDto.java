package com.akfly.hzz.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TradeInfoDto{

    private Integer id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品id 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 商品名称
     */
    private String gbiName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 预购价格
     */
    private Double price;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 成功购买数量
     */
    private Integer successNum;

    /**
     * 1委托买入 2正常买入 3委托卖出 4正常卖出
     */
    private Integer type;

    /**
     * 1已委托,初始状态 2 已取消 3部分成功 4交易成功 5交易失败 6 交易中
     */
    private Integer tradeStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 下单时间
     */
    private LocalDateTime tradeTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishtime;


    private String gbiPicture;


}
