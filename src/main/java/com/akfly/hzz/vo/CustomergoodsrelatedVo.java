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
 * 客户商品物料对应表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customergoodsrelated")
public class CustomergoodsrelatedVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增
     */
      @TableId(value = "Id", type = IdType.AUTO)
    private Long Id;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 物料id
     */
    private Long giiId;

    /**
     * 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 0不拥有  1拥有
     */
    private Integer cgrIsown;

    /**
     * 0未锁住 1锁住
     */
    private Integer cgrIslock;

    /**
     * 0未提货 1已提货
     */
    private Integer cgrIspickup;

    private LocalDateTime cgrBuytime;

    private LocalDateTime cgrSelltime;

    private LocalDateTime cgrUpdatetime;


}
