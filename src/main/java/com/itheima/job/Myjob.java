package com.itheima.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Myjob {
    //@Scheduled(fixedDelay = 1000)
    //XXljob分布式的任务的调度，每次只执行一次
   // @Scheduled(cron =" 0/2 * * * * *")
    public   void   test(){
        System.out.println("hello.everybody"+System.currentTimeMillis());
    }
}
