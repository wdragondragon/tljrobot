package com.jdragon.tljrobot.tlj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication(exclude= {DataSourceAutoConfigurat`ion.class})
@SpringBootApplication
@MapperScan("com.jdragon.tljrobot.tlj.mappers")
@EnableTransactionManagement
@EnableScheduling
@EnableSwagger2
public class TljApplication {
    public static void main(String[] args) {
        SpringApplication.run(TljApplication.class, args);
    }
}