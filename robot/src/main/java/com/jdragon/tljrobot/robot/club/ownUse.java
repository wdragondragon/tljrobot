package com.jdragon.tljrobot.robot.club;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import com.jdragon.tljrobot.tljutils.getShell.shell;

public class ownUse extends IcqListener {
    boolean sign = false;
    @EventHandler
    public void carry(EventPrivateMessage event){
        if(event.getSender().getId()==1061917196L){
            if(sign){
                if("#shell".equals(event.getMessage())){
                    sign = false;
                    event.respond("关闭shell");
                }else {
                    event.respond(shell.getShellExecuted(event.getMessage()));
                }
            }else {
                if("#shell".equals(event.getMessage())){
                    sign = true;
                    event.respond("开启shell");
                }
            }
        }
    }
}
