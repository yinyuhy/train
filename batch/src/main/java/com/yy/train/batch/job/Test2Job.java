package com.yy.train.batch.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//禁止任务并发执行
@DisallowConcurrentExecution
public class Test2Job implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("quartz job2222 开始");
        System.out.println("quartz job2222 结束");
    }
}
