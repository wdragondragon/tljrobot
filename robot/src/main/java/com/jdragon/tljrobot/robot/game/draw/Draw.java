package com.jdragon.tljrobot.robot.game.draw;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.26 09:02
 * @Description:
 */
public class Draw extends IcqListener {
    private static HashMap<Long, DrawEntity> recordMap = new HashMap<>();
    @EventHandler
    public void carry(EventPrivateMessage eventPrivateMessage){
        String message = eventPrivateMessage.getMessage();
        Long id = eventPrivateMessage.getSenderId();
        String [] s = message.split("\\s");
        if("#抽签".equals(message)){
            DrawEntity drawEntity = recordMap.get(id);
            List<String> recordList = drawEntity.getRecordList();
            if(recordList==null||recordList.isEmpty()){
                eventPrivateMessage.respond("无记录");
                return;
            }
            Random random = new Random();
            int randomNum = random.nextInt(recordList.size());
            eventPrivateMessage.respond(recordList.get(randomNum));
            if(drawEntity.isDelete()) {
                recordList.remove(randomNum);
            }
            eventPrivateMessage.respond("剩余:\n"+getSurplusRecord(id));
        }else if("#抽后剔除".equals(message)){
            DrawEntity drawEntity = recordMap.get(id);
            if(drawEntity==null){
                eventPrivateMessage.respond("无记录无法设置");
                return;
            }
            drawEntity.setDelete(!drawEntity.isDelete());
            eventPrivateMessage.respond(drawEntity.isDelete()?"抽后删除":"抽后不删除");
        }else if("#记录".equals(message)){
            eventPrivateMessage.respond("已有记录:\n"+getSurplusRecord(id));
        }else if("#抽签帮助".equals(message)){
            eventPrivateMessage.respond("#记录 = 查看当前已添加记录\n" +
                    "#记录 记录1 记录2 记录3 = 增加这三条要抽签的记录\n" +
                    "#抽签 = 抽取随机记录\n" +
                    "#抽后剔除 = 改变抽签后是否剔除");
        }else if("#记录".equals(s[0])){
            DrawEntity drawEntity = recordMap.get(id);
            List<String> recordList;
            if(drawEntity==null) {
                recordList = new ArrayList<>();
                drawEntity = new DrawEntity(recordList, false);
            }else{
                recordList = drawEntity.getRecordList();
            }
            for (int i = 1; i < s.length; i++) {
                recordList.add(s[i]);
            }
            recordMap.put(id, drawEntity);
            eventPrivateMessage.respond("添加成功：\n" + getSurplusRecord(id));
        }
    }
    private String getSurplusRecord(Long id){
        StringBuffer stringBuffer = new StringBuffer();
        DrawEntity drawEntity = recordMap.get(id);
        if(drawEntity==null){
            return "无";
        }
        List<String> recordList = drawEntity.getRecordList();
        for (String record : recordList){
            stringBuffer.append(record).append("\n");
        }
        return stringBuffer.toString();
    }
    @Test
    public void test(){
        Random random = new Random();
        while (true){
            int randomNum = random.nextInt(5 - 1);
            System.out.println(randomNum);
        }
    }
}
