package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.CustomerpickupinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户提货表 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomerpickupinfoService extends IService<CustomerpickupinfoVo> {

    public void saveCustomerpickupinfo(CustomerpickupinfoVo customerpickupinfoVo) throws HzzBizException;

    public void pickup(long cbiid,long gbid,int num,long caiid) throws HzzBizException;

    public List<CustomerpickupinfoVo> listCustomerpickupinfo(long cbiid) throws HzzBizException;

    List<CustomerpickupinfoVo> getCustomerpickupinfos(long cbiid, int pageNum, int pageSize) throws HzzBizException;

    int getPickUpNum(long cbiid, long gbid);
}
