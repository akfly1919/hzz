package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易行情日期
 * </p>
 *
 * @author wangfei
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reporttradedate")
public class ReporttradedateVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


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
     * 0-24
     */
    private Integer rtiHour;

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

    @TableField(exist = false)
    private String time;

}
