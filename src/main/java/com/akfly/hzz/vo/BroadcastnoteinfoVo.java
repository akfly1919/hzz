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
 * 轮播信息
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("broadcastnoteinfo")
public class BroadcastnoteinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "bni_id", type = IdType.AUTO)
    private Integer bniId;

    /**
     * 位置 1首页轮播 2交易大厅轮播 3 通知广播
     */
    private Integer bniPostion;

    /**
     * 轮播图片
     */
    private String bniPicture;

    /**
     * 标题
     */
    private String bniTitle;

    /**
     * 描述
     */
    private String bniDesc;

    /**
     * 链接
     */
    private String bniUrl;

    /**
     * 排序
     */
    private Integer bniSort;

    private LocalDateTime bniCreatetime;

    private LocalDateTime bniUpdatetime;

    /**
     * 0不可用 1可用
     */
    private Integer bniValid;


}
