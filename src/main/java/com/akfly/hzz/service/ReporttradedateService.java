package com.akfly.hzz.service;

import com.akfly.hzz.vo.ReporttradedateVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易行情日期 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-01
 */
public interface ReporttradedateService extends IService<ReporttradedateVo> {

    /**
     * 通过商品id查询销售数量
     * @param gbiId
     * @return
     * @throws HzzBizException
     */
    int getRtiNum(long gbiId);

}
