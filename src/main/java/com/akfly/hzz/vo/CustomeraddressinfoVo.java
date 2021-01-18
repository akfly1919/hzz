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
 * 用户地址信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customeraddressinfo")
public class CustomeraddressinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "cai_id", type = IdType.AUTO)
    private Integer caiId;

    /**
     * 用户id
     */
    private Long cbiId;

    private String caiRecipients;

    private String caiPhonenum;

    private String caiProvince;

    private String caiCity;

    private String caiArea;

    private String caiStreet;

    /**
     * 排序
     */
    private Integer caiSort;

    private LocalDateTime caiCreatetime;

    private LocalDateTime caiUpdatetime;

    /**
     * 0不可用 1可用
     */
    private Integer caiValid;


}
