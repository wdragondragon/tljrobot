package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.window.MainFra;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.*;
import java.awt.event.KeyEvent;


public class QqOperation {

    public static final int GET_ARTICLE = 1;
    public static final int SEND_ACHIEVEMENT = 2;
    public static void start(int i,String  Name)throws Exception{
        Robot robot = new Robot();
        HWND hWnd= User32.INSTANCE.FindWindow("TXGuiFoundation", Name);

        HWND genda = User32.INSTANCE.FindWindow(null, MainFra.getInstance().getTitle());
        robot.delay(150);
        if(hWnd!=null) {

            User32.INSTANCE.SetForegroundWindow(hWnd);    //切换到聊天窗口
            if (i == GET_ARTICLE) {
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);

                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyPress(KeyEvent.VK_C);

                robot.keyRelease(KeyEvent.VK_A);
                robot.keyRelease(KeyEvent.VK_C);
                robot.keyRelease(KeyEvent.VK_CONTROL);

            } else if (i == SEND_ACHIEVEMENT) {
                for(char s:Clipboard.get().toCharArray()) {
                    User32.INSTANCE.PostMessage(hWnd, User32.WM_CHAR, new WinDef.WPARAM(s), new WinDef.LPARAM(0));
                }
//                robot.keyPress(KeyEvent.VK_CONTROL);
//                robot.keyPress(KeyEvent.VK_V);
//                robot.keyRelease(KeyEvent.VK_V);
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

            }
            Thread.sleep(300);
            User32.INSTANCE.SetForegroundWindow(genda);
        }
    }
}
