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
 * 用户提货表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerpickupinfo")
public class CustomerpickupinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 提货单号
     */
      @TableId(value = "cpui_orderid", type = IdType.AUTO)
    private Long cpuiOrderid;

    /**
     * 用户ID
     */
    private Long cbiId;

    /**
     * 物料id
     */
    private Long giiId;

    /**
     * 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 用户地址ID
     */
    private Integer caiId;

    private LocalDateTime cpuiCreatetime;

    private LocalDateTime cpuiUpdatetime;

    private LocalDateTime cpuiFinishtime;

    /**
     * 0待审核 1已审核 2拒绝  3待发货,4已发货 ，5已收货(已提货)
     */
    private Integer cpuiStatus;


}
