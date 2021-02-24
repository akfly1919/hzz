package com.akfly.hzz.task;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.service.CustomergoodsrelatedService;
import com.akfly.hzz.service.TradegoodsellService;
import com.akfly.hzz.service.TradepredictinfoService;
import com.akfly.hzz.service.TradetimeService;
import com.akfly.hzz.util.DateUtil;
import com.akfly.hzz.vo.TradetimeVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Lazy(value = false)
@Component
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleTask implements SchedulingConfigurer {

    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public static String endtime;
    public static String parten;
    public static int RELEASSIZE=50;
    @Resource
    TradepredictinfoService tradepredictinfoService;
    @Resource
    CustomergoodsrelatedService customergoodsrelatedService;

    @Resource
    private TradegoodsellService tradegoodsellService;

    @Resource
    TradetimeService tradetimeService;
    private void releasTradepredictinfo()  {
        try{
            int dealsize=0;
            do{
                dealsize=tradepredictinfoService.releas(RELEASSIZE);
            }while(dealsize==RELEASSIZE);

        }catch(Exception e){
            e.printStackTrace();
        }
        getNewTradeTime();
    }
    private void unlockCustomergoodsrelated(){
        try {
            customergoodsrelatedService.unlock();
        } catch (HzzBizException e) {
            e.printStackTrace();
        }
    }
    private void getNewTradeTime(){
        try {
            TradetimeVo tt = tradetimeService.getTradeTime();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            if(tt.getTtTimePmEnd().equalsIgnoreCase(endtime)){
                return;
            }
            endtime=tt.getTtTimePmEnd();

            Date time = sdf.parse(tt.getTtTimePmEnd());
             parten=time.getSeconds()+" "+time.getMinutes()+" "+time.getHours()+" * * ?";

        } catch (HzzBizException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void tradeTask() {

        Date endTime = new Date();
        Date beginTime = DateUtils.addDays(endTime, -7);  // 扫描最近7天的卖出挂单，来触发定时撮合交易
        tradegoodsellService.tradeTask(beginTime, endTime);
    }

    //每日0点更新当日当日定时器时间
    //读取交易时间，设置定时器启动时间为交易结束时间
    //保证每日定时器启动时，为最新的交易配置时间

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        getNewTradeTime();
        scheduledTaskRegistrar.addTriggerTask(getReleaseTask(),getReleaseTrigger());
        scheduledTaskRegistrar.addCronTask(getUnlockCustomergoodsrelatedTask(),"0 */5 * * * ?");
        scheduledTaskRegistrar.addCronTask(combineTradeTask(),"0 */5 * * * ?");
    }

    private Runnable getUnlockCustomergoodsrelatedTask(){
        return new Runnable() {
            @Override
            public void run() {
                unlockCustomergoodsrelated();
            }
        };
    }
    private Runnable getReleaseTask(){
        return new Runnable() {
            @Override
            public void run() {
                releasTradepredictinfo();
            }
        };
    }

    private Runnable combineTradeTask(){
        return new Runnable() {
            @Override
            public void run() {
                tradeTask();
            }
        };
    }

    private static Trigger getReleaseTrigger(){
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(parten);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };

    }


}
