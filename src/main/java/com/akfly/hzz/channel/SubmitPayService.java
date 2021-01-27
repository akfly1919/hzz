package com.akfly.hzz.channel;

import com.akfly.hzz.exception.HzzBizException;

import java.util.Map;


public abstract class SubmitPayService<T> {

    /**
     * 生成请求第三方请求参数
     *
     * @param model
     * @return
     */
    protected abstract Map generateReqParam(T model) throws HzzBizException;

    /**
     * 将支付订单推送到第三方
     *
     * @param param
     * @return
     */
    protected abstract Map channelPay(Map param) throws HzzBizException;

    /**
     * 获取返回结果
     * 
     * @param param
     * @return
     */
    protected abstract SubmitPayResultModel getResult(T model, Map param) throws HzzBizException;

    public SubmitPayResultModel submitPay(T model) throws HzzBizException {
        Map reqMap = generateReqParam(model);
        Map channelResult = channelPay(reqMap);
        return getResult(model, channelResult);
    }
}
