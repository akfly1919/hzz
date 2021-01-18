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
 * 交易配置信息 如费率等
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradeconfig")
public class TradeconfigVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 费率
     */
    private Double tcRate;

    /**
     * 1.服务费
     */
    private Integer tcType;

    /**
     * 0不可用  1可用
     */
    private Integer tcValid;

    /**
     * 规则开始时间
     */
    private LocalDateTime tcStartime;

    /**
     * 规则结束时间
     */
    private LocalDateTime tcEndtime;

    /**
     * 创建时间
     */
    private LocalDateTime tcCreatetime;

    /**
     * 更新时间
     */
    private LocalDateTime tcUpdatetime;

    private String tcOperator;


}
