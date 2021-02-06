package com.akfly.hzz.mapper;

import com.akfly.hzz.vo.CustomergoodsrelatedVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 客户商品物料对应表 Mapper 接口
 * </p>
 *
 * @author wangfei
 * @since 2021-02-06
 */
public interface CustomergoodsrelatedMapper extends BaseMapper<CustomergoodsrelatedVo> {

    @Select("SELECT cbi_id,cgr.gbi_id,count(gii_id) as stock,gbi.gbi_name as name,gbi.gbi_price as price FROM customergoodsrelated cgr left join goodsbaseinfo gbi on(gbi.gbi_id=cgr.gbi_id) where cbi_id=#{id} and cgr.cgr_isown=1 and cgr.cgr_islock=0 and cgr.cgr_ispickup=0 group by cgr.gbi_id,cbi_id ")
    List<CustomergoodsrelatedVo> listStockCanSold(Serializable id);
}
