package com.akfly.hzz.service.impl;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.vo.GoodsbaseinfoVo;
import com.akfly.hzz.vo.TradetimeVo;
import com.akfly.hzz.mapper.TradetimeMapper;
import com.akfly.hzz.service.TradetimeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * <p>
 * 交易时间配置 服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-01-18
 */
@Service
public class TradetimeServiceImpl extends ServiceImpl<TradetimeMapper, TradetimeVo> implements TradetimeService {

    @Resource
    TradetimeMapper tradetimeMapper;
    public TradetimeVo getTradeTime() throws HzzBizException {
        QueryWrapper<TradetimeVo> contract_wrapper = new QueryWrapper<TradetimeVo>();
        contract_wrapper.eq("tt_status","1");
        TradetimeVo tt= tradetimeMapper.selectOne(contract_wrapper);
        if(tt==null){
            throw new HzzBizException(HzzExceptionEnum.DB_ERROR);
        }
        return tt;
    }
    public boolean isInTradeTime(String time) throws HzzBizException {
        TradetimeVo tt=this.getTradeTime();
        if(DateUtil.isEffectiveTime(time,tt.getTtTimeAmStart(),tt.getTtTimeAmEnd())||DateUtil.isEffectiveTime(time,tt.getTtTimePmStart(),tt.getTtTimePmEnd())){
            return true;
        }
        return false;
    }

    public HashMap<String ,LocalDateTime> getRealTradeStartTime() throws HzzBizException {
        HashMap<String ,LocalDateTime> timemap=new HashMap<>();
        LocalDateTime now =LocalDateTime.now();
        TradetimeVo tt=this.getTradeTime();
        String nowTime=now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        int range = timeRange(nowTime);
        if(range == 1){
            // 当前时间晚于下午交易结束时间则加一天
            now=now.plusDays(1);

        }
        String date=now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String starttime=date+" "+tt.getTtTimeAmStart();
        String finishtime=date+" "+tt.getTtTimePmEnd();
        if (range == 0) {
            starttime = date + " " + tt.getTtTimePmStart();
        }
        DateTimeFormatter df =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(starttime,df);
        LocalDateTime finish = LocalDateTime.parse(finishtime,df);
        timemap.put("startTime",start);
        timemap.put("finishTime",finish);
        return timemap;
    }

    /**
     * 将时间段分为三段  -1表示小于当天最早交易开始时间  0表示在上午结束时间和下午开始时间之间
     * 1表示大于当天最晚交易结束时间,2表示在正常交易时间段内
     * @param time
     * @return
     * @throws HzzBizException
     */
    public int timeRange(String time) throws HzzBizException {

        TradetimeVo tt=this.getTradeTime();
        if(DateUtil.compareOnlyByTime(time, tt.getTtTimePmEnd()) >= 0){
            return 1;
        } else if (DateUtil.compareOnlyByTime(time, tt.getTtTimeAmStart()) < 0) {
            return -1;
        } else if (DateUtil.isEffectiveTime(time, tt.getTtTimeAmEnd(), tt.getTtTimePmStart())) {
            return 0;
        } else {
            return 2;
        }

    }

}
