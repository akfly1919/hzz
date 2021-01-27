package com.akfly.hzz.mapper;

import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * <p>
 * 用户基础信息 Mapper 接口
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
public interface CustomerbaseinfoMapper extends BaseMapper<CustomerbaseinfoVo> {

    @Select("select * from `customerbaseinfo` where cbi_id=#{id} for update")
    CustomerbaseinfoVo selectByIdForUpdate(Serializable id);
}
