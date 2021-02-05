package com.akfly.hzz.service.impl;

import com.akfly.hzz.dto.UserGoodsDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.mapper.CustomergoodsrelatedMapper;
import com.akfly.hzz.service.CustomeraddressinfoService;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.GoodsbaseinfoService;
import com.akfly.hzz.util.RandomGenUtils;
import com.akfly.hzz.vo.*;
import com.akfly.hzz.mapper.CustomerpickupinfoMapper;
import com.akfly.hzz.service.CustomerpickupinfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户提货表 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class CustomerpickupinfoServiceImpl extends ServiceImpl<CustomerpickupinfoMapper, CustomerpickupinfoVo> implements CustomerpickupinfoService {
    @Resource
    private CustomergoodsrelatedMapper customergoodsrelatedMapper;
    @Resource
    private CustomergoodsrelatedService customergoodsrelatedService;
    @Resource
    private CustomeraddressinfoService customeraddressinfoService;
    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;
    @Transactional(rollbackFor = Exception.class)
    public void pickup(long cbiid,long gbid,int num,long caiid) throws HzzBizException {
        GoodsbaseinfoVo gi = goodsbaseinfoService.getGoodsbaseinfoVo(gbid);
        if(num<gi.getGbiLimitpickup()){
            throw new HzzBizException(HzzExceptionEnum.LIMIT_PICKUP_ERROR);
        }
        CustomeraddressinfoVo cai = customeraddressinfoService.getAddressInfoByCaiId(cbiid,caiid);
        if(cai==null){
            throw new HzzBizException(HzzExceptionEnum.GOODS_DEFAULT_ADDRESS_ERROR);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("cbi_id",cbiid);
        map.put("gbi_id",gbid);
        map.put("cgr_isown",1);
        map.put("cgr_ispickup",0);
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.allEq(map);
        wrapper_c.in("cgr_islock",0,1);
        wrapper_c.last("limit "+num);
        //wrapper_c.lt("cgr_buytime", LocalDate.now());
        wrapper_c.last("for update");
        List<CustomergoodsrelatedVo> cgrlist = customergoodsrelatedMapper.selectList(wrapper_c);
        if(cgrlist==null||cgrlist.size()<num){
            throw new HzzBizException(HzzExceptionEnum.STOCK_ERROR);
        }
        for(CustomergoodsrelatedVo cgr:cgrlist){
            cgr.setCgrIspickup(1);
            cgr.setCgrUpdatetime(LocalDateTime.now());
            customergoodsrelatedService.saveCustomergoodsrelated(cgr);
            CustomerpickupinfoVo cpi=new CustomerpickupinfoVo();
            cpi.setCpuiTrackingnumber("");
            cpi.setCpuiTracktype("");

            cpi.setCbiId(cgr.getCbiId());
            cpi.setGiiId(cgr.getGiiId());
            cpi.setGbiId(cgr.getGbiId());
            cpi.setCaiId(cai.getCaiId());
            cpi.setCpuiCreatetime(LocalDateTime.now());
            cpi.setCpuiUpdatetime(LocalDateTime.now());
            cpi.setCpuiFinishtime(LocalDateTime.now());
            cpi.setCpuiStatus(0);
            saveCustomerpickupinfo(cpi);

        }
    }
    public void saveCustomerpickupinfo(CustomerpickupinfoVo customerpickupinfoVo) throws HzzBizException {
        if(!saveOrUpdate(customerpickupinfoVo)) {
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
    }
    public List<CustomerpickupinfoVo> listCustomerpickupinfo(long cbiid) throws HzzBizException {
        QueryWrapper wrapper_c=new QueryWrapper();
        wrapper_c.select("cbi_id,gbi_id,cai_id,cpui_status,count(gii_id) as stock");
        wrapper_c.eq("cbi_id",cbiid);
        wrapper_c.groupBy("cbi_id","gbi_id","cai_id","cpui_status","cpui_trackingnumber");
        List<CustomerpickupinfoVo> list = getBaseMapper().selectList(wrapper_c);
        for (CustomerpickupinfoVo cpi : list) {
            GoodsbaseinfoVo vo = goodsbaseinfoService.getGoodsbaseinfoWithRedis(cpi.getGbiId());
            cpi.setGbi(vo);
        }
        return list;
    }
}
