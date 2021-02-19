package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("contactus")
public class ContactusVo implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String phone;

    private String weixin;

    private LocalDateTime createtime;


}
