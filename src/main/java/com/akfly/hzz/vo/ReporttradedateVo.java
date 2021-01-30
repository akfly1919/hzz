package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交易行情日期
 * </p>
 *
 * @author wangfei
 * @since 2021-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reporttradedate")
public class ReporttradedateVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 年
     */
    private Integer rtiYear;

    /**
     * 月
     */
    private Integer rtiMonth;

    /**
     * 周
     */
    private Integer rtiWeek;

    /**
     * 日期
     */
    private Integer rtiDate;

    /**
     * 物品id
     */
    private Long rtiGbid;

    /**
     * 物品名称
     */
    private String ritGbiname;

    /**
     * 交易量
     */
    private Integer rtiNum;

    /**
     * 交易金额
     */
    private Double rtiMoney;

    private LocalDateTime rtiCreatetime;

    private LocalDateTime rtiUpdatetime;


}
