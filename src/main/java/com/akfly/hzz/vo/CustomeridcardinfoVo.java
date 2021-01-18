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
 * 用户身份证信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customeridcardinfo")
public class CustomeridcardinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long cbiId;

    private String ciiIdcardfront;

    private String ciiIdcardback;

    private LocalDateTime ciiCreatetime;

    private LocalDateTime ciiUpdatetime;

    /**
     * 1未审核 2审核
     */
    private Integer ciiStatus;

    /**
     * 操作人
     */
    private String ciiOperator;


}
