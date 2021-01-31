package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomercashoutinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户提现表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomercashoutinfoService extends IService<CustomercashoutinfoVo> {
    public void createcustomercashoutinfo(CustomercashoutinfoVo customercashoutinfoVo) throws HzzBizException;

    List<CustomercashoutinfoVo> getWithdraw(Long userId, int pageSize, int pageNum) throws HzzBizException;
}
