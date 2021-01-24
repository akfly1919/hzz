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
 * 客户账单流水对应表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerbillrelated")
public class CustomerbillrelatedVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "cbr_id", type = IdType.AUTO)
    private Long cbrId;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 传入支付ID/交易id/提现id
     */
    private String cbrorderid;

    /**
     * 金额
     */
    private Double cbrMoney;

    /**
     * 1收入  2支出
     */
    private Integer cbrType;

    /**
     * 创建时间
     */
    private LocalDateTime cbrCreatetime;

    private LocalDateTime cbrUpdatetime;


}
