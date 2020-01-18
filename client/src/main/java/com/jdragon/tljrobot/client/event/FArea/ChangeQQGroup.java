package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.config.LocalSystem;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.utils.common.WindowAPI;

import java.util.List;

public class ChangeQQGroup {
    private ChangeQQGroup(){}
    private static int i = 0;
    private static List<String> QQWindowsName;
    public static void start(){
        // TODO Auto-generated method stub
        try{
            if(!LocalSystem.isWindows())return;
            if(i==0)QQWindowsName  = WindowAPI.getQQWindows();
            SwingSingleton.QQNameLabel().setText(QQWindowsName.get(i++));
            i = i%(QQWindowsName.size());
        }catch(Exception e){ i=0;SwingSingleton.QQNameLabel().setText("获取QQ窗口错误");}
    }
}