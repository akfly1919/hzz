package com.akfly.hzz.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户关系表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerrelationinfo")
public class CustomerrelationinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户cbi_id
     */
    private String cbiId;

    /**
     * 用户cbi_id
     */
    private String criMember;

    /**
     * 层级:1,2
     */
    private Integer criLevel;

    /**
     * 0未认证 1认证
     */
    private Integer criIsidentify;

    /**
     * 0不可用，1可用
     */
    private Integer criIsvalid;

    /**
     * 加入时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criJointime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criCreatetime;

    /**
     * 1常规
     */
    private Integer criScale;


}
