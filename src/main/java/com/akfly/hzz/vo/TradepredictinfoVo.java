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
 * 交易市场买方预买表
 * </p>
 *
 * @author wangfei
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tradepredictinfo")
public class TradepredictinfoVo implements Serializable {

    private static final long serialVersionUID=1L;
    public static int TYPE_NOMAL=2;
    public static int TYPE_ENTRUST=1;
    public static int STATUS_PARTIAL_SUCCESS=3;
    public static  int STATUS_SUCCESS=4;
    public static int STATUS_ENTRUST=1;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预购单ID
     */
    private String tpiId;

    /**
     * 商品id 例如:某品牌价格红酒
     */
    private Long gbiId;

    /**
     * 买货人cbi_id
     */
    private Long tpiBuyerid;

    /**
     * 预购价格
     */
    private Double tpiPrice;

    /**
     * 购买数量
     */
    private Integer tpiNum;

    /**
     * 成功购买数量
     */
    private Integer tpiSucessnum;

    /**
     * 1委托买入 2正常买入
     */
    private Integer tpiType;

    /**
     * 1已委托,初始状态 2 已取消 3部分成功 4交易成功 5交易失败
     */
    private Integer tpiStatus;

    /**
     * 创建时间
     */
    private LocalDateTime tpiCreatetime;

    /**
     * 预购买时间
     */
    private LocalDateTime tpiBuytime;

    /**
     * 预购完成时间
     */
    private LocalDateTime tpiFinishtime;

    /**
     * 撮合交易完成时间
     */
    private LocalDateTime tpiTradetime;

    /**
     * 更新时间
     */
    private LocalDateTime tpiUpdatetime;

    /**
     * 预购服务费金额
     */
    private Double tpiServicefee;


}
