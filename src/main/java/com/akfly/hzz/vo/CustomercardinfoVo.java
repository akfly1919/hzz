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
 * 用户账户信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customercardinfo")
public class CustomercardinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增账号id
     */
      @TableId(value = "cci_id", type = IdType.AUTO)
    private Long cciId;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 用户总资产
     */
    private Double cciTotal;

    /**
     * 用户余额
     */
    private Double cciBalance;

    /**
     * 会员积分
     */
    private Integer cciPointreward;

    /**
     * 提成金额
     */
    private Double cciTrainreword;

    /**
     * 购买次数
     */
    private Integer cciBuynum;

    /**
     * 提货次数
     */
    private Integer cciPicknum;

    private LocalDateTime cciCreatetime;

    private LocalDateTime cciUpdatetime;

    /**
     * 正常用户
     */
    private Integer cciType;


}
