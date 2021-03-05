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
 * 商品物料信息-最细颗粒度
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goodsiteminfo")
public class GoodsiteminfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 物料id
     */
      @TableId(value = "gii_id", type = IdType.AUTO)
    private Long giiId;

    /**
     * 商品id
     */
    private Long gbiId;

    private LocalDateTime giiCreatetime;

    /**
     * 0不可用 1可用
     */
    private Integer giiStatus;

    /**
     * 0未提货 1已提货 2已发货 3已收货 提货后不可用
     */
    private Integer giiIspickup;

    /**
     * 提货时间
     */
    private LocalDateTime giiPickuptime;

    private LocalDateTime giiReceivetime;

    private LocalDateTime giiUpdatetime;

    private Integer giiIsshow;


}
