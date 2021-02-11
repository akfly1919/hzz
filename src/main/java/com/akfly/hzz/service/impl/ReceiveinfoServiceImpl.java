package com.akfly.hzz.service.impl;

import com.akfly.hzz.vo.ReceiveinfoVo;
import com.akfly.hzz.mapper.ReceiveinfoMapper;
import com.akfly.hzz.service.ReceiveinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 收款二维码 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
@Service
@Slf4j
public class ReceiveinfoServiceImpl extends ServiceImpl<ReceiveinfoMapper, ReceiveinfoVo> implements ReceiveinfoService {

    @Override
    public List<ReceiveinfoVo> getReceiveinfoVo() {

        List<ReceiveinfoVo> list = lambdaQuery().eq(ReceiveinfoVo::getRiStatus, 1).list();
        return list;
    }
}
