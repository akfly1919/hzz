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
     * 资源地址
     */
    private String              resource;

    /**
     * 返回内容
     */
    private Map<String, String> retContent;

    //public OutputTypeEnum getOutputType() {
    //    return outputType;
    //}
    //
    //public void setOutputType(OutputTypeEnum outputType) {
    //    this.outputType = outputType;
    //}

}
