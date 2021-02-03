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
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradeconfig")
public class TradeconfigVo implements Serializable {

    private static final long serialVersionUID=1L;
    public static int TCTYPE_BUY=5;

    public static int TCTYPE_SELL=6;
    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 费率，任务目标数量
     */
    private Double tcRate;

    /**
     * 1.提现服务费2.购买任务数量3.提货任务数量4.特价商品数量5买入手续费，6卖出手续费
     */
    private Integer tcType;

    /**
     * 0不可用  1可用
     */
    private Integer tcValid;

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
