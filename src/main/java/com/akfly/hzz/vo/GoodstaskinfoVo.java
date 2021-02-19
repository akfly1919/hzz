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
 * 商品任务信息
 * </p>
 *
 * @author wangfei
 * @since 2021-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goodstaskinfo")
public class GoodstaskinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增主键
     */
      @TableId(value = "gti_id", type = IdType.AUTO)
    private Long gtiId;

    /**
     * 商品id
     */
    private Long gbiId;

    /**
     * 商品购买任务个数
     */
    private Integer gtiBuynum;

    /**
     * 商品提货任务个数
     */
    private Integer gtiPickupnum;

    /**
     * 奖励特价任务数量
     */
    private Integer gtiDiscountnum;

    private LocalDateTime gtiCreatetime;

    private LocalDateTime gtiUpdatetime;


}
