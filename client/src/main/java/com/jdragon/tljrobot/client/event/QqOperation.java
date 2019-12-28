package com.jdragon.tljrobot.client.event;

import com.jdragon.tljrobot.client.window.MainFra;
import com.sun.jna.platform.WindowUtils;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.util.User32;

import java.awt.*;
import java.awt.event.KeyEvent;

public class QqOperation {
    public static final int GET_ARTICLE = 1;
    public static final int SEND_ACHIEVEMENT = 2;
    public static void start(int i,String  Name)throws Exception{
        Robot robot = new Robot();
        HWND hWnd= User32.FindWindow("TXGuiFoundation", Name);
        HWND genda = User32.FindWindow(null, MainFra.getInstance().getTitle());
        robot.delay(150);
        if(hWnd.getValue()>0) {
            java.lang.String winname = User32.GetWindowText(hWnd);
            System.out.println(winname);
            User32.SetForegroundWindow(hWnd);    //切换到聊天窗口
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
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
            do {
                Thread.sleep(300);
                User32.SetForegroundWindow(genda);
                hWnd = User32.GetForegroundWindow();
                winname = User32.GetWindowText(hWnd);
            } while (!winname.equals(MainFra.getInstance().getTitle()));
        }
    }
}
