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
 * 交易时间配置
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradetime")
public class TradetimeVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 交易开始时间,截取此表时分秒对比
     */
    private LocalDateTime ttStarttime;

    /**
     * 交易结束时间, 截取此表时分秒对比
     */
    private LocalDateTime ttEndtime;

    /**
     * 0不可用  1可用
     */
    private Integer ttStatus;


}
