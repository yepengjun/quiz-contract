package com.conflux.contract;

import com.conflux.contract.monitor.ContractLogMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author xiaolong
 * @description:1.主要用于标记配置类，兼备Component的效果.2.开启定时任务
 * @date 2020-07-05-04:20
 */
@Configuration
@EnableScheduling
public class ScheduleTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ContractLogMonitorService contractLogMonitorService;
    /**
     * 3.添加定时任务
     */
    @Scheduled(cron = "0/2 * * * * ?")
    /**
     * @Scheduled(fixedRate=5000)
     */
    private void configureTasks() {
        logger.info("执行静态定时任务时间: " + LocalDateTime.now());
        contractLogMonitorService.monitorNftLog();
    }
}
