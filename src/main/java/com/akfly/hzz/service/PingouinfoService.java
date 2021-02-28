package com.akfly.hzz.service;

import com.akfly.hzz.dto.*;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.vo.PingouinfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 拼购奖励 服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-27
 */
public interface PingouinfoService extends IService<PingouinfoVo> {

    /**
     * 获取拼购明细列表
     * @param cbiId
     * @param status 0:全部 1: 拼购中 2: 已完成
     * @return
     * @throws HzzBizException
     */
    List<PingouinfoVo> getPinGouInfos(Long cbiId, int status, int pageSize, int pageNum) throws HzzBizException;


    /**
     * 获取培养明细列表
     * @param cbiId
     * @param type 1: 1级奖励 2: 2级奖励
     * @return
     * @throws HzzBizException
     */
    List<PingouinfoVo> getPeiYangInfos(Long cbiId, int type, int pageSize, int pageNum) throws HzzBizException;


    List<PinGouSumDto> getPinGouSumByGbiId(Long cbiId);


    List<PeiYangSumDto> getPeiYangSumByGbiId(Long cbiId);


    TeamAndTradeDto getMyTeamDto(Long cbiId);


}
