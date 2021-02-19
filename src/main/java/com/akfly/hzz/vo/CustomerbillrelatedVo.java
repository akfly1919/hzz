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
 * 客户账单流水对应表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
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
     * 1.收入 2 支持
     */
    private Integer cbrType;

    /**
     * 创建时间
     */
    private LocalDateTime cbrCreatetime;

    private LocalDateTime cbrUpdatetime;

    /**
     * 1.充值收入，2.商品收入,3.提现支出,4.卖货服务费,5.买货服务费,6提现手续费,7.买货支出
     */
    private Integer cbrClass;


}
