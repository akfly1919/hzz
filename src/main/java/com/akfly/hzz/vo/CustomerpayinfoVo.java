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
 * 用户充值信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerpayinfo")
public class CustomerpayinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "cpi_orderid", type = IdType.AUTO)
    private Long cpiOrderid;

    /**
     * 账户充值金额
     */
    private Integer caiAmount;

    /**
     * customerbaseinfo的cbi_id
     */
    private String cbiId;

    private LocalDateTime cpiCreatetime;

    private LocalDateTime cpiUpdatetime;

    private LocalDateTime cpiPaytime;

    private LocalDateTime cpiFinishtime;

    /**
     * 充值状态0未付款 1已付款
     */
    private Integer cpiPaystatus;

    /**
     * 0无效 1有效
     */
    private Integer cpiValid;

    /**
     * 充值渠道1.weixin, 2.Alipay
     */
    private Integer cpiChannel;

    /**
     * 充值平台返回的订单号
     */
    private String cpiChannelorderid;

    /**
     * 审核人
     */
    private String cpiOperator;


}
