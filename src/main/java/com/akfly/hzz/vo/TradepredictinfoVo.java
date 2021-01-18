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
 * 交易预购买表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradepredictinfo")
public class TradepredictinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预购单ID
     */
    private String tpiId;

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
    private Long tpiBuyerid;

    /**
     * 售卖人cbi_id
     */
    private Long tpiSellerid;

    /**
     * 售卖价格
     */
    private Double tpiPrice;

    /**
     * 1委托买入 2正常买入
     */
    private Integer tpiType;

    /**
     * 1已委托 2 已取消 3交易成功 4交易失败
     */
    private Integer tpiStatus;

    /**
     * 预购买时间
     */
    private LocalDateTime tpiCreatetime;

    /**
     * 预购买时间
     */
    private LocalDateTime tpiBuytime;

    private LocalDateTime tpiUpdatetime;

    /**
     * 完成交易时间
     */
    private LocalDateTime tpiFinishtime;


}
