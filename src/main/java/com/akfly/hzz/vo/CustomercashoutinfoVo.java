package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户提现表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customercashoutinfo")
public class CustomercashoutinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 提货单号
     */
      @TableId(value = "ccoi_id", type = IdType.AUTO)
    private Long ccoiId;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 提现账户信息
     */
    @NotBlank
    private String ccoiAccount;

    /**
     * 账户类型 1支付宝 2微信 3银行卡
     */
    @Min(1)
    @Max(3)
    private Integer ccoiType;

    /**
     * 提现金额
     */
    @Digits(integer = 10,fraction = 2)
    private Double ccoiAmount;

    /**
     * 描述
     */
    private String ccoiDesc;

    /**
     * 1待审核2已审核3已拒绝4审核通过5已打款 6打款失败
     */

    private Integer ccoiStatus;

    private LocalDateTime ccoiCreatetime;

    private LocalDateTime ccoiUpdatetime;

    private LocalDateTime ccoiFinishtime;

    /**
     * 审核人
     */
    private String ccoiOperator;


}
