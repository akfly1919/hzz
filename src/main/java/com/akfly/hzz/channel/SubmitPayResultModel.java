package com.akfly.hzz.channel;


import lombok.Data;

import java.util.Map;

/**
 * 支付返回结果
 * 
 */

@Data
public class SubmitPayResultModel {

    /**
     * 输出类型
     */
    //private OutputTypeEnum      outputType;

    /**
     * 返回结果串
     */
    private String              result;

    /**
     * 返回内容
     */
    //private Map<String, String> retContent;


}
