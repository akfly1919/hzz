package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.vo.TradegoodsellVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户商品物料对应表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomergoodsrelatedServiceImpl extends ServiceImpl<CustomergoodsrelatedMapper, CustomergoodsrelatedVo> implements CustomergoodsrelatedService {

    @Override
    public int getStock(long gbiId) {

        int stock = lambdaQuery().eq(CustomergoodsrelatedVo::getGbiId, gbiId)
                .eq(CustomergoodsrelatedVo::getCgrIsown, 1).eq(CustomergoodsrelatedVo::getCgrIslock, 0).count();
        return stock;
    }

    public CustomergoodsrelatedVo selectCustomergoodsrelatedVoForUpdate(Map<String,Object> condition){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.allEq(condition);
        wrapper.last("for update");
        return getBaseMapper().selectOne(wrapper);
    }

    public void saveCustomergoodsrelated(CustomergoodsrelatedVo customergoodsrelatedVo) throws HzzBizException {
        if(!saveOrUpdate(customergoodsrelatedVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }

    public List<CustomergoodsrelatedVo> listStockCanSold(long cbiid){
        return getBaseMapper().listStockCanSold(cbiid);
    }
    @Transactional(rollbackFor = Exception.class)
    public void unlock() throws HzzBizException {
        Map<String,Object> map=new HashMap<>();
        map.put("cgr_isown",1);
        map.put("cgr_islock",1);
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.allEq(map);
        //wrapper_c.lt("cgr_buytime", LocalDate.now());
        wrapper_c.last("for update");
        List<CustomergoodsrelatedVo> cgrlist = getBaseMapper().selectList(wrapper_c);

        for(CustomergoodsrelatedVo cgr:cgrlist){
            cgr.setCgrIslock(0);
            saveCustomergoodsrelated(cgr);
        }
    }
}
