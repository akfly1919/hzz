package com.akfly.hzz.service.impl;

import com.akfly.hzz.dto.*;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.service.*;
import com.akfly.hzz.vo.*;
import com.akfly.hzz.mapper.PingouinfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拼购奖励 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-27
 */
@Service
@Slf4j
public class PingouinfoServiceImpl extends ServiceImpl<PingouinfoMapper, PingouinfoVo> implements PingouinfoService {

    @Resource
    private GoodsbaseinfoService goodsbaseinfoService;

    @Resource
    private CustomerrelationinfoService customerrelationinfoService;

    @Resource
    private TradeorderinfoService tradeorderinfoService;

    @Resource
    private CustomerpickupinfoService customerpickupinfoService;

    @Override
    public List<PingouinfoVo> getPinGouInfos(Long cbiId, int status, int pageSize, int pageNum) throws HzzBizException {

        List<PingouinfoVo> list = null;
        if (status == 0) {
            list = lambdaQuery().in(PingouinfoVo::getPgiStatus, 1, 2)
                    .ge(PingouinfoVo::getPgiCreatetime, LocalDate.now())
                    .eq(PingouinfoVo::getPgiLevel1, cbiId)
                    .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        } else {
            list = lambdaQuery().eq(PingouinfoVo::getPgiStatus, status)
                    .ge(PingouinfoVo::getPgiCreatetime, LocalDate.now())
                    .eq(PingouinfoVo::getPgiLevel1, cbiId)
                    .last("limit " + pageNum * pageSize + "," + pageSize + " ").list();
        }
        return list;

    }

    @Override
    public List<PingouinfoVo> getPeiYangInfos(Long cbiId, int type, int pageSize, int pageNum) throws HzzBizException {

        List<PingouinfoVo> list = null;
        if (type == 1) {
            list = lambdaQuery().eq(PingouinfoVo::getPgiLevel1, cbiId)
                    .ge(PingouinfoVo::getPgiCreatetime, LocalDate.now())
                    .last("limit " + pageNum * pageSize + "," + pageSize + " ")
                    .list();
        } else if (type == 2) {
            list = lambdaQuery().eq(PingouinfoVo::getPgiLevel2, cbiId)
                    .ge(PingouinfoVo::getPgiCreatetime, LocalDate.now())
                    .last("limit " + pageNum * pageSize + "," + pageSize + " ")
                    .list();
        }
        return list;
    }

    @Override
    public List<PinGouSumDto> getPinGouSumByGbiId(Long cbiId) {

        LocalDate now = LocalDate.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<PingouinfoVo> list = getPingouinfoVoGroupGbiId(cbiId, date);
        List<PinGouSumDto> dtos = new ArrayList<>();
        for (PingouinfoVo vo : list) {
            PinGouSumDto dto = new PinGouSumDto();
            dto.setGbiId(vo.getGbiId());
            PingouinfoVo finished = getFinished(cbiId, vo.getGbiId(), date);
            //int isFinished = (finished.getGbiNum() >= 5) ? 1 : 0;
            dto.setIsFinished(1);
            dto.setUnFinishedNum(5 - finished.getGbiNum() % 5);
            dto.setFinishedNum(finished.getGbiNum());
            dto.setRewordNum(finished.getGbiNum() / 5 + 1);
            try {
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                dto.setGbiName(goods.getGbiName());
                dto.setGbiPicture(goods.getGbiPicture());
            } catch (HzzBizException e) {
                log.error("未获取到商品信息", e);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<PeiYangSumDto> getPeiYangSumByGbiId(Long cbiId) {

        LocalDate now = LocalDate.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<PingouinfoVo> list = getPingouinfoVoGroupGbiId(cbiId, date);
        List<PeiYangSumDto> dtos = new ArrayList<>();
        for (PingouinfoVo vo : list) {
            PeiYangSumDto dto = new PeiYangSumDto();
            PingouinfoVo finishedL1 = getFinishedLevel(cbiId, vo.getGbiId(), date, 1);
            dto.setIsFinished(1);
            dto.setOneLevelFinishedNum(finishedL1.getGbiNum());
            PingouinfoVo finishedL2 = getFinishedLevel(cbiId, vo.getGbiId(), date, 2);
            dto.setTowLevelFinishedNum(finishedL2.getGbiNum());
            try {
                GoodsbaseinfoVo goods = goodsbaseinfoService.getGoodsbaseinfoWithRedis(vo.getGbiId());
                dto.setGbiName(goods.getGbiName());
                dto.setGbiPicture(goods.getGbiPicture());
            } catch (HzzBizException e) {
                log.error("未获取到商品信息", e);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public TeamAndTradeDto getMyTeamDto(Long cbiId) {

        List<CustomerrelationinfoVo> list = customerrelationinfoService.getCustomerrelationinfoVo(cbiId, 0);
        List<Long> userIds = new ArrayList<Long>();
        for (CustomerrelationinfoVo vo : list) {
            userIds.add(Long.valueOf(vo.getCriMember()));
        }
        TeamAndTradeDto teamAndTradeDto = new TeamAndTradeDto();
        if (userIds.size() > 0) {
            LocalDate now = LocalDate.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            int finishedNum = getFinishedNum(date, userIds);
            MyTeamDto myTeamDto = new MyTeamDto();
            myTeamDto.setNum(list.size());
            myTeamDto.setFinishedNum(finishedNum);

            CustomerrelationinfoVo teamVo = customerrelationinfoService.getTeamLevel(cbiId);
            myTeamDto.setLevel(teamVo.getCriLevel() == 1 ? "常规" : "未知");
            myTeamDto.setActiveNum(tradeorderinfoService.getActiveUser(userIds));
            HistoryTradeDto historyTradeDto = new HistoryTradeDto();
            historyTradeDto.setBuyNum(tradeorderinfoService.getAmount(userIds, 0, 0));
            historyTradeDto.setSellNum(tradeorderinfoService.getAmount(userIds, 0, 1));
            historyTradeDto.setSpecialBuyNum(tradeorderinfoService.getAmount(userIds, 1, 0));
            historyTradeDto.setPickupNum(customerpickupinfoService.getPickUpAmount(userIds));
            teamAndTradeDto.setMyTeamDto(myTeamDto);
            teamAndTradeDto.setHistoryTradeDto(historyTradeDto);
        }
        return teamAndTradeDto;
    }


    private List<PingouinfoVo> getPingouinfoVoGroupGbiId(long cbiId, String date) {

        QueryWrapper<PingouinfoVo> queryWrapper = new QueryWrapper<PingouinfoVo>();
        queryWrapper.eq("cbi_id", cbiId);
        queryWrapper.eq("pgi_date", date);
        queryWrapper.select(" gbi_id, count(pgi_key) as gbi_num ");
        queryWrapper.groupBy("gbi_id");
        List<PingouinfoVo> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    private PingouinfoVo getFinished(long cbiId, long gbiId, String date) {

        QueryWrapper<PingouinfoVo> queryWrapper = new QueryWrapper<PingouinfoVo>();
        queryWrapper.eq("pgi_level1", cbiId);
        queryWrapper.eq("gbi_id", gbiId);
        queryWrapper.eq("pgi_date", date);
        queryWrapper.eq("pgi_status", 2);
        queryWrapper.select(" gbi_id, count(pgi_key) as gbi_num ");
        PingouinfoVo vo = baseMapper.selectOne(queryWrapper);
        return vo;

    }

    private PingouinfoVo getFinishedLevel(long cbiId, long gbiId, String date, int level) {

        QueryWrapper<PingouinfoVo> queryWrapper = new QueryWrapper<PingouinfoVo>();
        if (level == 1) {
            queryWrapper.eq("pgi_level1", cbiId);
        } else {
            queryWrapper.eq("pgi_level2", cbiId);
        }
        queryWrapper.eq("gbi_id", gbiId);
        queryWrapper.eq("pgi_date", date);
        queryWrapper.select(" gbi_id, count(pgi_key) as gbi_num ");
        PingouinfoVo vo = baseMapper.selectOne(queryWrapper);
        return vo;

    }


    private int getFinishedNum(String date, List<Long> userIds) {

        int count = lambdaQuery().in(PingouinfoVo::getCbiId, userIds).eq(PingouinfoVo::getPgiDate, date)
                .eq(PingouinfoVo::getPgiStatus, 2).count();
        return count;

    }
}
