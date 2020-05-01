package com.jdragon.tljrobot.robot.typing;

import cc.moecraft.icq.sender.IcqHttpApi;
import com.jdragon.tljrobot.robot.club.robot;
import com.jdragon.tljrobot.robot.newTyping.tools.GroupCache;
import com.jdragon.tljrobot.tljutils.DateUtil;

import java.util.Calendar;

public class Automatic_Inclusion extends Thread {
    @Override
    public void run() {
        IcqHttpApi httpApi =  robot.getInstance().getAccountManager().getNonAccountSpecifiedApi();
        while(true) {
            try {
                sleep(60000);
                Calendar calendar = Calendar.getInstance();
                if(calendar.get(Calendar.HOUR_OF_DAY)==23&&calendar.get(Calendar.MINUTE)==55) {
                    RobotGroupClient.automati_inclusion_sign = true;
                    for(Long o: GroupCache.groupCardCache.keySet()){
                        if(o.equals(robot.tljGroupNum)){
                            continue;
                        }
                        httpApi.sendGroupMsg(o, "#成绩");
                        RobotGroupClient.grouplist.put(o,true);
                    }

                    IcqHttpApi icqHttpApi = robot.getInstance().getAccountManager().getNonAccountSpecifiedApi();
                    String path = com.jdragon.tljrobot.robot.newTyping.RobotGroupClient.resultUnionJson(DateUtil.now(),false);
                    if(path.equals("无该天赛文成绩")) {
                        icqHttpApi.sendGroupMsg(robot.tljGroupNum,path);
                    } else {
                        icqHttpApi.sendGroupMsg(robot.tljGroupNum,"[CQ:image,file=" + path + "]");
                    }

                    path = com.jdragon.tljrobot.robot.newTyping.RobotGroupClient.resultTljJson(DateUtil.now());
                    if(path.equals("无该天赛文成绩")) {
                        icqHttpApi.sendGroupMsg(robot.tljGroupNum,path);
                    } else {
                        icqHttpApi.sendGroupMsg(robot.tljGroupNum,"[CQ:image,file=" + path + "]");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
