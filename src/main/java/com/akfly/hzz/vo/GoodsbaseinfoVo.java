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
 * 商品基础信息-用于展示
 * </p>
 *
 * @author wangfei
 * @since 2021-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goodsbaseinfo")
public class GoodsbaseinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 产品名称ID 例如:某品牌价格红酒
     */
      @TableId(value = "gbi_id", type = IdType.AUTO)
    private Long gbiId;

    /**
     * 产品名称
     */
    private String gbiName;

    /**
     * 1酒类2茶水 3电子设备
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
     * 0不可以 1可用
     */
    private Integer gbiValid;

    /**
     * 1正常商品 2新手商品
     */
    private Integer gbiType;

    /**
     * 0未上架 1上架
     */
    private Integer gbiSaleable;

    private LocalDateTime gbiCreatetime;

    private LocalDateTime gbiUpdatetime;

    private String gbiOperator;

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
     * 售卖冻结天数
     */
    private Integer gbiFrozendays;


}
