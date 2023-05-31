package com.jdragon.tljrobot.tlj.config;

import com.jdragon.tljrobot.tlj.service.ArticlePoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author JDragon
 * @date 2023/5/31 11:57
 * @description
 */
@Slf4j
@Component
public class ArticlePoolCollectCreator implements CommandLineRunner {

    @Autowired
    private ArticlePoolService articlePoolService;

    @Override
    public void run(String... args) throws Exception {
        log.info("开机启动定时任务中。。。。。");
        ExecutorService executor = Executors.newFixedThreadPool(1); // 创建大小为1的线程池
        executor.submit(() -> {
            while (true) {
                try {
                    articlePoolService.insertNewContent();
                } catch (Exception e) {
                    log.error("插入新文章失败", e);
                }
            }
        }); // 提交任务到线程池
        log.info("开机定时任务启动成功。。。。。");
    }
}
