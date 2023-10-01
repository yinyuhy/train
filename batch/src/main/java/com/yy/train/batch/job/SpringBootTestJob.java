package com.yy.train.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 适合单体应用，不适合集群，保证高可用，部署多个节点的话会重复
 * 没法实时更改定时任务状态和策略
 * 解决方案：增加分布式锁
 */
@Component
@EnableScheduling
public class SpringBootTestJob{

    @Scheduled(cron = "0/5 * * * * ?")
    private void test() {
        System.out.println("Spring boot test job");
    }
}
