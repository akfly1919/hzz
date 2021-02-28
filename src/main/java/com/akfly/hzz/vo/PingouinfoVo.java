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
 * 拼购奖励
 * </p>
 *
 * @author wangfei
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pingouinfo")
public class PingouinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "pgi_id", type = IdType.AUTO)
    private Integer pgiId;

    /**
     * 主键日期用户id商品
     */
    private String pgiKey;

    /**
     * 日期
     */
    private String pgiDate;

    /**
     * 用户id
     */
    private Long cbiId;

    /**
     * 商品id
     */
    private Long gbiId;

    /**
     * 数量
     */
    private Integer gbiNum;

    /**
     * 商品价格
     */
    private Double gbiPrice;

    /**
     * 商品价值积分
     */
    private Double gbiValue;

    /**
     * 1拼购中 2已完成
     */
    private Integer pgiStatus;

    /**
     * 创建时间
     */
    private LocalDateTime pgiCreatetime;

    /**
     * 更新时间
     */
    private LocalDateTime pgiUpdatetime;

    /**
     * 父级id只到L1
     */
    private Long pgiLevel1;

    /**
     * 祖父级id到L2
     */
    private Long pgiLevel2;

    /**
     * 1级奖励
     */
    private Double pgiReward1;

    /**
     * 2级奖励
     */
    private Double pgiReward2;


}
