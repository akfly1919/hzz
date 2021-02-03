package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交易时间配置
 * </p>
 *
 * @author wangfei
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradetime")
public class TradetimeVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上午交易开始时间
     */
    private String ttTimeAmStart;

    /**
     * 上午交易结束时间
     */
    private String ttTimeAmEnd;

    /**
     * 下午交易开始时间
     */
    private String ttTimePmStart;

    /**
     * 下午交易结束时间
     */
    private String ttTimePmEnd;

    /**
     * 0不可用  1可用
     */
    private Integer ttStatus;


}
