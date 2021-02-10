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
    public ReceiveinfoVo getReceiveinfoVo(int type) {

        log.info("获取收款二维码信息入参 type={}", type);
        List<ReceiveinfoVo> list = lambdaQuery().eq(ReceiveinfoVo::getRiType, type).eq(ReceiveinfoVo::getRiStatus, 1).list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
