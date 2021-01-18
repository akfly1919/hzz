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
 * 用户团队信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerteaminfo")
public class CustomerteaminfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "cti_id", type = IdType.AUTO)
    private Integer ctiId;

    /**
     * cbi_ID
     */
    private Long ctiOwner;

    private LocalDateTime ctiCreatetime;

    private LocalDateTime ctiUpdatetime;

    /**
     * 1常规
     */
    private Integer ctiScale;

    /**
     * 0不可用 1可用
     */
    private Integer ctiValid;


}
