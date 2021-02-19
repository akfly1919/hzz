package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.GoodstaskinfoVo;
import com.akfly.hzz.mapper.GoodstaskinfoMapper;
import com.akfly.hzz.service.GoodstaskinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.LongLongSeqHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品任务信息 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-13
 */
@Service
@Slf4j
public class GoodstaskinfoServiceImpl extends ServiceImpl<GoodstaskinfoMapper, GoodstaskinfoVo> implements GoodstaskinfoService {

    @Override
    public GoodstaskinfoVo getGoodstaskinfoVo(Long gbiid) throws HzzBizException {

        GoodstaskinfoVo defaultTaskInfo = null;
        List<GoodstaskinfoVo> list = lambdaQuery().in(GoodstaskinfoVo::getGbiId, gbiid, -1).list();
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i ++) {
                if (list.get(i).getGbiId().longValue() == gbiid) {
                    return list.get(i);
                }
                if (list.get(i).getGbiId() == -1) {
                    defaultTaskInfo = list.get(i);
                }
            }
            return defaultTaskInfo;
        } else if (list.size() == 1) {
            return list.get(0); // 默认的配置
        } else {
            throw new HzzBizException(HzzExceptionEnum.TASK_NOT_CONFIG_ERROR);  // 没有默认配置也没有商品的配置
            //return null;
        }
    }
}
