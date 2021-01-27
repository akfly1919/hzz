package com.akfly.hzz.channel;

import lombok.Data;


@Data
public class BaseChAppParamModel {

    /**
     * 第三方应用编号
     */
    private String chAppId;
    /**
     * 第三方商户编号
     */
    private String chMchId;
    /**
     * 第三方商户子号
     */
    private String chSubMchId;

    /**
     * 第三方应用秘钥，RSA加密时存放私钥
     */
    private String chAppKey;

    /**
     * 第三方商户秘钥，RSA加密时存放第三方公钥
     */
    private String chMchKey;


    // 私钥
    private String privateKey;

    // 公钥
    private String publicKey;

    // 支付宝系统商编号
    private String chPid;

}
