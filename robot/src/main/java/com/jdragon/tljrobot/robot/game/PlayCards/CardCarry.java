package com.jdragon.tljrobot.robot.game.PlayCards;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventMessage;

public class CardCarry extends IcqListener {
    CardClient c = new CardClient();
    @EventHandler
    public void Carry(EventMessage event){
        c.setEvent(event);
        String message = event.getMessage();
        String s[] = message.split(" ");
        Long id = event.getSenderId();
        String idcard = event.getSender().getInfo().getNickname();
        String type = "QQ";

        if("斗地主创房".equals(message)){
            int roomnum = c.CreatRoom(id,idcard,type);
            if(roomnum==-1) {
                event.respond("创建失败");
            } else {
                event.respond("创建成功，房间号"+roomnum);
            }
        }
        else if(s.length==2&& "加入斗地主房间".equals(s[0])){
            int roomnum = Integer.valueOf(s[1]);
            if(!c.isFull(roomnum)){
                if (c.JoinRoom(id, idcard, type, roomnum)) {
                    event.respond("加入" + roomnum + "房成功");
                } else {
                    event.respond("加入失败");
                }
            }
            else{
                event.respond("房间已满");
            }
        }
        else if ("退出斗地主房间".equals(message)){
            if(c.OutRoom(id)) {
                event.respond("退出成功");
            }
            else {
                event.respond("退出失败");
            }
        }
        else if("准备".equals(message)){
            if(c.ChangeReady(id)){
                event.respond("准备");
            }
            else {
                event.respond("操作失败");
            }
            if(c.isAllReady(id)) {
                c.start(id);
            }
        }
    }
}
