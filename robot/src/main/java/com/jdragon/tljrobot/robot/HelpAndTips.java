package com.jdragon.tljrobot.robot;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentAt;

/**
 * Create by Jdragon on 2020.02.13
 */
public class HelpAndTips extends IcqListener {
    @EventHandler
    public void carry(EventGroupMessage eventGroupMessage) {
        String message = eventGroupMessage.getMessage();
        if (message.contains("跟打器") && message.contains("下载")) {
            eventGroupMessage.respond(new ComponentAt(eventGroupMessage.getSenderId()).toString() + "\n长流跟打器，你值得拥有,跟打器地址：\nhttps://tlj.wiki");
        } else if (message.contains("长流")
                && (message.contains("错误") || message.contains("不行") || message.contains("用不了") || message.contains("打不开") || message
                .contains("问题"))) {
            eventGroupMessage.respond(new ComponentAt(eventGroupMessage.getSenderId()).toString() + "\n先上长流官网看看有没有解决方案吧！\nhttps://tlj.wiki/home/blog");
        } else if (message.equals("#人工智障帮助")) {
            eventGroupMessage.respond("群映射列表 = 查询各大跟打群映射的缩写名字\n" +
                    "#查询 = 查询自己的赛文上屏成绩概况" +
                    "#查询 @QQ = 查询某qq号的赛文上屏成绩概况 \n" +
                    "#刷新名片 = 刷新成绩单上的名片\n" +
                    "#长流 长流用户名 = 查询长流某用户的详情\n" +

                    "-----排名指令-----\n" +
                    "#长流均速排名\n" +
                    "#长流赛文均速排名\n" +
                    "#长流生稿均速排名\n" +

                    "-----群赛指令-----\n" +
                    "？+ 要查询的字词 = 该词小鹤的最佳编码\n" +
                    "例：？奇怪 = qg\n" +
                    "-----群赛指令-----\n" +

                    "#历史赛文 yyyy-mm-dd = 查看本群的某日比赛赛文\n" +
                    "#历史成绩 yyyy-MM-dd = 查看本群的某日比赛成绩（图片成绩）\n" +

                    "-----联赛指令-----\n" +
                    "#联赛 = 获取今天的联赛赛文\n" +
                    "#联赛成绩 = 查看今天的联赛成绩\n" +
                    "#统计成绩 查看统计所有群列表的日赛成绩汇总\n" +

                    "-----长流指令-----\n" +
                    "#长流 长流号名 = 查询你长流中账号信息\n" +
                    "#长流成绩 = 查看今天的长流生稿成绩\n" +

                    "-----战场指令-----\n" +
                    "#随机战场 启动不需要跟打器的QQ私聊作对照区，Q群聊天框作跟打区的对战模式\n" +
                    "#随机混战 启动一个以个人为单位计分的跟打发文\n" +
                    "#随机团战 启动一个以队伍为单位计分的跟打发文\n" +

                    "-----游戏指令-----\n" +
                    "1A2B = 启动以个人为单位的1A2B游戏（默认4个数）\n" +
                    "1A2B 个位数 = 启动以个人为单位的1A2B游戏（指定位数）"

            );
        }
    }
}
