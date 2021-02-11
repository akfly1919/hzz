package com.akfly.hzz.service;

import com.akfly.hzz.vo.ReceiveinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 收款二维码 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
public interface ReceiveinfoService extends IService<ReceiveinfoVo> {


    List<ReceiveinfoVo> getReceiveinfoVo();
}
