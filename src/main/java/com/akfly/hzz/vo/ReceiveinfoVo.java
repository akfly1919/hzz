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
 * 收款二维码
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("receiveinfo")
public class ReceiveinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付二维码/图片
     */
    private String riPicture;

    /**
     * 支付类型1支付宝2微信3银行卡
     */
    private Integer riType;

    /**
     * 描述
     */
    private String riDes;

    /**
     * 0不可用 1可用
     */
    private Integer riStatus;

    private LocalDateTime riCreatetime;

    private LocalDateTime riUpdatetime;

    /**
     * 操作人
     */
    private String riOperator;


}
