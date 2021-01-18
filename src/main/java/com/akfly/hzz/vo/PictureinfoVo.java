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
 * 图片信息与商品关联(goodsbaseinfo)
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pictureinfo")
public class PictureinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 图片ID
     */
      @TableId(value = "pi_id", type = IdType.AUTO)
    private Long piId;

    /**
     * 产品名称ID 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 图片地址
     */
    private String piPath;

    /**
     * 图片描述
     */
    private String piDesc;

    /**
     * 图片类型 1小图 2大图 3.详情
     */
    private Integer piType;

    /**
     * 0不可用 1可用
     */
    private Integer piValid;

    /**
     * 图片排序
     */
    private Integer piSort;

    private LocalDateTime piCreatetime;

    private LocalDateTime piUpdatetime;

    private String piOperator;


}
