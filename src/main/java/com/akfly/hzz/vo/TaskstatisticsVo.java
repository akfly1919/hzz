package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务指标统计表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("taskstatistics")
public class TaskstatisticsVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    private Long gbiId;

    /**
     * 买货人id
     */
    private Long cbiId;

    /**
     * 买入数量
     */
    private Integer buyNum;

    /**
     * 提货数量
     */
    private Integer pickupNum;

    /**
     * 已使用买入数量
     */
    private Integer usedBuyNum;

    /**
     * 已使用提货数量
     */
    private Integer usedPickupNum;

    /**
     * 创建时间
     */
    private LocalDateTime toiCreatetime;

    /**
     * 更新时间
     */
    private LocalDateTime toiUpdatetime;


}
