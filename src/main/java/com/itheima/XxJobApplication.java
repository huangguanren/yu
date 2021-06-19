package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
  //@EnableScheduling
public class XxJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(XxJobApplication.class,args);
    }
}
