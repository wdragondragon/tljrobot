package com.jdragon.tljrobot.tljutils;

import java.util.*;

/**
 * Create by Jdragon on 2020.01.18
 */
public class TimingList<T> extends ArrayList<T> {
    // 存放过期日期的map
    private Map<String, Long> timingMap = new HashMap<>();
    // 过期时间
    private final long expireTime = 60*60*1000;
    public TimingList(){
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
    public void removeExpireData(List<String> keys) {
        for (String key : keys) {
            timingMap.remove(key);
            super.remove((T) key);
        }
    }
    @Override
    public boolean add(T t){
        timingMap.put((String)t,System.currentTimeMillis() + expireTime);
        return super.add(t);
    }
    @Override
    public T remove(int i){
        timingMap.remove(super.get(i));
        return super.remove(i);
    }
    @Override
    public boolean remove(Object o){
        timingMap.remove(o);
        return super.remove((T)o);
    }
    @Override
    public void clear(){
        super.clear();
        timingMap.clear();
    }
}
