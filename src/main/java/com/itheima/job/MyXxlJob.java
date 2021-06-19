package com.itheima.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

@Component
@JobHandler(value = "job.test")
public class MyXxlJob extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        System.out.println("hello.everybody"+System.currentTimeMillis());
        return SUCCESS;
    }
}
