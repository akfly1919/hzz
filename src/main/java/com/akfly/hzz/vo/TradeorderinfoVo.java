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
 * 交易市场买货订单
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradeorderinfo")
public class TradeorderinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 交易市场订单id
     */
    private String toiOrderid;

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
     * 买货人cbi_id
     */
    private Long tgsBuyerid;

    /**
     * 售卖人cbi_id
     */
    private Long toiSellerid;

    /**
     * 售卖价格
     */
    private Double toiPrice;

    /**
     * 1未付款 2已付款 3 取消交易
     */
    private Integer toiStatus;

    /**
     * 购买时间
     */
    private LocalDateTime toiBuytime;

    private LocalDateTime toiUpdatetime;

    /**
     * 完成交易时间
     */
    private LocalDateTime toiFinishtime;


}
