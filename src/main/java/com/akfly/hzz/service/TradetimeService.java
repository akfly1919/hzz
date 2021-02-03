package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradetimeVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易时间配置 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface TradetimeService extends IService<TradetimeVo> {
    public TradetimeVo getTradeTime() throws HzzBizException;
}
