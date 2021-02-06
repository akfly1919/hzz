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
 * 任务信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("taskinfo")
public class TaskinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 商品id
     */
    private Long gbiId;

    /**
     * 描述
     */
    private String tiTarget;

    /**
     * 如每天购买次数10次同类产品;
     */
    private Integer tiCount;

    /**
     * 0未完成 1已完成 2已生效
     */
    private Integer tiStatus;

    /**
     * 1购买任务2提货任务3特价商品
     */
    private Integer tiType;

    /**
     * 规则开始时间
     */
    private LocalDateTime tiStarttime;

    /**
     * 规则结束时间
     */
    private LocalDateTime tiEndtime;

    /**
     * 更新时间
     */
    private LocalDateTime tiUpdatetime;


}
