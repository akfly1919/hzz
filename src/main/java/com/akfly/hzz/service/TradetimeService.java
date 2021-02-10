package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.TradetimeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.HashMap;

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
    public boolean isInTradeTime(String time) throws HzzBizException;
    public HashMap<String , LocalDateTime> getRealTradeStarttime() throws HzzBizException;
}
