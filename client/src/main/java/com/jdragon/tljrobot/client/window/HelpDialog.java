package com.jdragon.tljrobot.client.window;

import javax.swing.*;

/**
 * Create by Jdragon on 2020.02.02
 */
public class HelpDialog {
    public static void showHelp(){
        JOptionPane.showMessageDialog(MainFra.getInstance(),
                "F1对文本框内的群发送成绩\n"
                        +"F2选择本地发文\n"
                        +"F3重打\n"
                        +"F4载文\n"
                        +"F5换群\n"
                        +"F6分享发文\n"
                        +"F7暂停\n"
                        +"ctrl+L乱序功能\n"
                        +"ctrl+P发送下一段\n"
                        +"ctrl+Z进入设置\n"
                        +"ctrl+Q听打模式\n"
                        +"ctrl+W看打模式\n"
                        +"ctrl+G跟打模式\n"
                        +"ctrl+enter提交成绩后\n" +
                        "灰色代表打少字，粉色代表打多字，红色背景代表错误原字，蓝色为上屏错字\n"
                        +"\n词语提示颜色代表\n"
                        +"黄色二简，橘色三简，灰色全四码,斜体多选\n"
                        +"提供每日赛文，菜单中点击后等待五秒即可开始\n"
                        +"\n\t鸡龙出品"
                ,"使用帮助",JOptionPane.PLAIN_MESSAGE);
    }
}
