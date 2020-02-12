package com.jdragon.tljrobot.robot.typing;

import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import com.jdragon.tljrobot.robot.club.robot;

import java.util.Calendar;

public class Automatic_Inclusion extends Thread {
    @Override
    public void run() {
        IcqHttpApi httpApi =  robot.getInstance().getAccountManager().getNonAccountSpecifiedApi();
        while(true) {
            try {

                Calendar calendar = Calendar.getInstance();
                if(calendar.get(Calendar.HOUR_OF_DAY)==23&&calendar.get(Calendar.MINUTE)==40) {
                    RobotGroupClient.automati_inclusion_sign = true;
                    for(Long o:RobotGroupClient.grouplist.keySet()){
                        httpApi.sendGroupMsg(o, "#成绩");
                        RobotGroupClient.grouplist.put(o,true);
                    }

                    IcqHttpApi icqHttpApi = robot.getInstance().getAccountManager().getNonAccountSpecifiedApi();
                    String path = com.jdragon.tljrobot.robot.newTyping.RobotGroupClient.resultUnionJson(false);
                    if(path.equals("无该天赛文成绩")) icqHttpApi.sendGroupMsg(robot.tljGroupNum,path);
                    else icqHttpApi.sendGroupMsg(robot.tljGroupNum,new ComponentImage(path).toString());

                    path = com.jdragon.tljrobot.robot.newTyping.RobotGroupClient.resultTljJson();
                    if(path.equals("无该天赛文成绩"))icqHttpApi.sendGroupMsg(robot.tljGroupNum,path);
                    else icqHttpApi.sendGroupMsg(robot.tljGroupNum,new ComponentImage(path).toString());
                }
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
