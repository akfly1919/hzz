package com.akfly.hzz.service;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.GoodstaskinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品任务信息 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-13
 */
public interface GoodstaskinfoService extends IService<GoodstaskinfoVo> {

    GoodstaskinfoVo getGoodstaskinfoVo(Long gbiid) throws HzzBizException;

}
