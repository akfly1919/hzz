package com.akfly.hzz.vo;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户提现表
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("customercashoutinfo")
public class CustomercashoutinfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 提货单号
     */
      @TableId(value = "ccoi_id", type = IdType.AUTO)
      @ApiModelProperty(hidden = true)
    private Long ccoiId;

    /**
     * 用户ID
     */

    @ApiModelProperty(hidden = true)
    private Long cbiId;

    /**
     * 提现账户信息
     */
    /*@NotBlank*/
    @ApiModelProperty(hidden = true)
    private String ccoiAccount;

    /**
     * 账户类型 1支付宝 2微信 3银行卡
     */
    @Min(1)
    @Max(3)
    private Integer ccoiType;

    /**
     * 提现金额
     */
    @Digits(integer = 10,fraction = 2)
    private Double ccoiAmount;

    /**
     * 描述
     */
    private String ccoiDesc;

    /**
     * 1待审核2已审核3已拒绝4审核通过5已打款 6打款失败
     */
    @ApiModelProperty(hidden = true)
    private Integer ccoiStatus;
    @ApiModelProperty(hidden = true)
    private LocalDateTime ccoiCreatetime;
    @ApiModelProperty(hidden = true)
    private LocalDateTime ccoiUpdatetime;
    @ApiModelProperty(hidden = true)
    private LocalDateTime ccoiFinishtime;

    /**
     * 审核人
     */
    @ApiModelProperty(hidden = true)
    private String ccoiOperator;

    @TableField(exist = false)
    private String alipayAccountNo;

    @TableField(exist = false)
    private String alipayAccountName;

    @TableField(exist = false)
    private String bankName;

    @TableField(exist = false)
    private String bankAccountName;

    @TableField(exist = false)
    private String bankAccountNo;

    public String getCcoiAccount() {
        if(ccoiType!=null){
            JSONObject ob=new JSONObject();
            if("1".equalsIgnoreCase(ccoiType.toString())){
                if(StringUtils.hasText(this.alipayAccountNo)&&StringUtils.hasText(this.alipayAccountName)){
                    ob.put("alipayAccountNo",this.alipayAccountNo);
                    ob.put("alipayAccountName",this.alipayAccountName);
                }else{
                    return null;
                }

            }else if("3".equalsIgnoreCase(ccoiType.toString())){
                if(StringUtils.hasText(this.bankName)&&StringUtils.hasText(this.bankAccountName)&&StringUtils.hasText(this.bankAccountNo)){
                    ob.put("bankName",this.bankName);
                    ob.put("bankAccountName",this.bankAccountName);
                    ob.put("bankAccountNo",this.bankAccountNo);
                }else{
                    return null;
                }

            }else{
                return null;
            }
            return ob.toString();
        }else{
            return null;
        }
    }

    public void setCcoiAccount(String ccoiAccount) {
        this.ccoiAccount = ccoiAccount;
    }
}
