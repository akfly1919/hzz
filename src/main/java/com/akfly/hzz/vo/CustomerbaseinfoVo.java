package com.akfly.hzz.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户基础信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customerbaseinfo")
public class CustomerbaseinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "cbi_id", type = IdType.AUTO)
    private Long cbiId;

    private String cbiName;

    private String cbiIdcard;

    private String cbiPhonenum;

    private String cbiEmail;

    /**
     * 0是男 1是女
     */
    private Integer cbiSex;

    /**
     * 登录名
     */
    private String cbiUsername;

    /**
     * 登录密码
     */
    private String cbiPassword;

    /**
     * 0不可用 1可用
     */
    private Integer cbiValid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cbiCreatetime;

    private LocalDateTime cbiUpdatetime;

    /**
     * 总资产
     */
    private Double cbiTotal;

    /**
     * 余额
     */
    private Double cbiBalance;

    /**
     * 冻结金额
     */
    private Double cbiFrozen;

    /**
     * 1管理员2用户
     */
    private Integer cbiType;

    /**
     * 用户推荐人
     */
    private Long cbiParentid;

    /**
     * 0不是新手1是新手
     */
    private Integer cbiIsnew;

    /**
     * 库存数量
     */
    private Integer cbiGoodsnum;

    private String cbiShareurl;


}
