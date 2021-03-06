package com.akfly.hzz.vo;

import com.akfly.hzz.dto.UserGoodsDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户提货表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerpickupinfo")
public class CustomerpickupinfoVo implements Serializable {

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
     * 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 用户地址ID
     */
    private Integer caiId;

    /**
     * 商品数量
     */
    private Long cpuiNum;

    private LocalDateTime cpuiCreatetime;

    private LocalDateTime cpuiUpdatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime cpuiFinishtime;

    /**
     * 0待审核 1已审批(已发货) 2拒绝
     */
    private Integer cpuiStatus;

    /**
     * 快递单号
     */
    private String cpuiTrackingnumber;

    /**
     * 快递名称/类型
     */
    private String cpuiTracktype;

    @TableField(exist = false)
    private long stock;
    @TableField(exist = false)
    private GoodsbaseinfoVo gbi;
}
