package com.akfly.hzz.dto;

import com.akfly.hzz.vo.BroadcastnoteinfoVo;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import lombok.Data;

import java.util.List;

/**
 * @program: hzz
 * @description:
 * @author: wangfei171
 * @create: 2021-01-18 19:58
 **/
@Data
public class GoodInfoDto {

    private List<GoodsbaseinfoVo> zcgoods;

    private List<GoodsbaseinfoVo> xsgoods;

    private List<BroadcastnoteinfoVo> sys;

    private List<BroadcastnoteinfoVo> tzs;

    private List<BroadcastnoteinfoVo> hds;



}
