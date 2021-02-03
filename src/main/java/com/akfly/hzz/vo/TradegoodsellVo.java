package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交易市场卖方预卖表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradegoodsell")
public class TradegoodsellVo implements Serializable {

    private static final long serialVersionUID=1L;
    public static int STATUS_SUCCESS=2;
    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 交易市场卖货单id
     */
    private String tgsId;

    /**
     * 物料id
     */
    private Long giiId;

    /**
     * 商品id 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 售卖人cbi_id
     */
    private Long tgsSellerid;

    /**
     * 售卖价格
     */
    private Double tgsPrice;

    /**
     * 0未交易 1交易中 2交易成功 3已取消 4交易失败
     */
    private Integer tgsStatus;

    /**
     * 0未上架 1已上架
     */
    private Integer tgsSaleable;

    /**
     * 创建时间
     */
    private LocalDateTime tgsCreatetime;

    /**
     * 商品发布时间
     */
    private LocalDateTime tgsPublishtime;

    private LocalDateTime tgsUpdatetime;

    /**
     * 售卖结束时间
     */
    private LocalDateTime tgsFinshitime;

    /**
     * 1正常商品2新手3特价商品
     */
    private Integer tgsType;

    /**
     * 1系统2用户
     */
    private Integer tgsOwntype;

    /**
     * 卖货服务费金额
     */
    private Double tgsServicefee;

    /**
     * 成交时间
     */
    private LocalDateTime tgsTradetime;

    /**
     * 1.委托卖出2正常卖出
     */
    private Integer tgsSelltype;


}
