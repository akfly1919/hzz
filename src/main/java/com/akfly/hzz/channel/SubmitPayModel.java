package com.akfly.hzz.channel;


import com.akfly.hzz.constant.CliDeviceTypeEnum;
import com.akfly.hzz.constant.CreditCardLimitEnum;
import lombok.Data;

import java.util.Date;

@Data
public class SubmitPayModel extends BaseChAppParamModel {

    // 交易订单号
    private String              transId;

    // 实付金额
    private Long                payAmount;

    // 输出类型
    //private OutputTypeEnum      outputType;

    // 渠道编号
    private String              chId;

    // 商品名称
    private String              mchOrderName;

    // 商品详情
    private String              mchOrderDetail;

    // 下单IP地址
    //private String              remoteIp;

    private CreditCardLimitEnum limit_pay;

    // 商户订单开始时间
    private Date                mchOrderStartTime;

    // 商户订单超时时间
    private Date                mchOrderExpDate;

    private String              mhtReserved;

    // 用于区分不同应用场景，生成Detail发往支付宝
    private CliDeviceTypeEnum cliDeviceType;

}
