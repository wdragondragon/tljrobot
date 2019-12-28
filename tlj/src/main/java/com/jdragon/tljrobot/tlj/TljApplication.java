package com.jdragon.tljrobot.tlj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.jdragon.tljrobot.tlj.mappers")
@EnableSwagger2
public class TljApplication {
    public static void main(String[] args) {
        SpringApplication.run(TljApplication.class, args);
    }
}