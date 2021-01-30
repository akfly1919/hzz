package com.akfly.hzz.dto;

import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.PictureinfoVo;
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

    private GoodsbaseinfoVo gbi;

    private List<PictureinfoVo> pivs;

    // 库存量
    private int stock;

    // 销售量
    private int salesVolume;

}
