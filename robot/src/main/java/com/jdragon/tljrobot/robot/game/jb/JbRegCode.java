package com.jdragon.tljrobot.robot.game.jb;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventMessage;
import com.jdragon.tljrobot.tljutils.HttpUtil;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.04.13 17:51
 * @Description:
 */
public class JbRegCode extends IcqListener {
    @EventHandler
    public void getCode(EventMessage eventMessage){
        String message = eventMessage.getMessage();
        if("#jb激活1".equals(message)){
            eventMessage.respond(HttpUtil.doGet("https://www.breathcoder.cn/breath/code"));
        }else if("#jb激活2".equals(message)){
            eventMessage.respond(HttpUtil.doGet("https://www.breathcoder.cn/breath/jihuo"));
        }
    }
}
