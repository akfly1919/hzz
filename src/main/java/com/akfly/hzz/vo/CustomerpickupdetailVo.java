package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户提货明细表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerpickupdetail")
public class CustomerpickupdetailVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 提货单号
     */
      private Long cpuiOrderid;

    /**
     * 物料id
     */
    private Long giiId;


}
