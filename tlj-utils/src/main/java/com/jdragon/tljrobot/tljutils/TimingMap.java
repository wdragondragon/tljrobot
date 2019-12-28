package com.jdragon.tljrobot.tljutils;

import org.springframework.stereotype.Component;

import java.util.*;

public class TimingMap<K, V> extends HashMap<K, V> {
    // 存放过期日期的map
    private Map<String, Long> timingMap = new HashMap<String, Long>();
    // 过期时间
    private final long expireTime = 60*60*1000;

    /**
     * 重写put方法
     */
    @Override
    public V put(K key, V value) {
        timingMap.put((String) key, System.currentTimeMillis() + expireTime);
        return super.put(key, value);
    }

    /**
     * 初始化的时候
     */
    public TimingMap() {
        super();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() { // 创建一个新的计时器任务。
            @Override
            public void run() {
                List<String> removeList = new ArrayList<String>();
                for (String key : timingMap.keySet()) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime > timingMap.get(key)) {
                        removeList.add(key);
                    }
                }
                removeExpireData(removeList);
            }
        };
        timer.schedule(task, 1000, 60000); // 一分钟刷新一次
    }

    /**
     * 移除过期数据
     *
     * @param keys
     */
    public void removeExpireData(List<String> keys) {
        for (String key : keys) {
            timingMap.remove(key);
            super.remove(key);
        }
    }

    /**
     * 重写get方法
     */
    @Override
    public V get(Object key) {
        long currentTime = System.currentTimeMillis();
//        if (currentTime > timingMap.get(key)) {
//            timingMap.remove(key);
//            super.remove(key);
//            return null;
//        }
        timingMap.put((String)key,currentTime+expireTime);
        return super.get(key);
    }

    /**
     * 重写移除方法
     */
    @Override
    public V remove(Object key) {
        timingMap.remove(key);
        return super.remove(key);
    }

    /**
     * 重写清除方法
     */
    @Override
    public void clear() {
        timingMap.clear();
        super.clear();
    }

    /**
     * 添加一个定时清除方法，方便后面和task集成用配置cron表达式的方法清除数据
     */
    public void scheduledClear() {
        List<String> removeList = new ArrayList<String>();
        for (String key : timingMap.keySet()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > timingMap.get(key)) {
                removeList.add(key);
            }
        }
        removeExpireData(removeList);
    }
}