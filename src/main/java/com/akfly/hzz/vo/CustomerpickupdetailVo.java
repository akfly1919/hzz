package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户提货明细表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerpickupdetail")
public class CustomerpickupdetailVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 提货单号
     */
    private Long cpuiOrderid;

    /**
     * 物料id
     */
    private Long giiId;


}
