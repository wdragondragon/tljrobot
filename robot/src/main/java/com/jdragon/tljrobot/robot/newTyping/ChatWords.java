package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.robot.typing.Tools.RegexText;
import com.jdragon.tljrobot.tljutils.HttpUtil;

/**
 * Create by Jdragon on 2020.02.05
 */
public class ChatWords extends IcqListener {
    @EventHandler
    public void carryGroupMessage(EventGroupMessage eventGroupMessage){
        String message = eventGroupMessage.getMessage();
        long id = eventGroupMessage.getSenderId();
        if(RegexText.returnduan(message)==-1&&!message.contains("[CQ:")){
            HttpUtil.doPost(HttpAddr.CHAT_ADD_NUM_ADDR,String.valueOf(id),String.valueOf(message.length()));
        }
    }
}
