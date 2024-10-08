package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowAPI {
    public static List<String> getQQWindows() {
        List<String> allWindows = getAllWindows();
//        return allWindows;
        List<String> QQWindows = new ArrayList<>();
        ArrayList<HWND> HList = new ArrayList<>();
        for (String allWindow : allWindows) {
            HWND hWnd = User32.INSTANCE.FindWindow(LocalConfig.ntqqGetArticle ? "Chrome_WidgetWin_1" : "TXGuiFoundation", allWindow);
            if (hWnd != null) {
                HList.add(hWnd);
                QQWindows.add(allWindow);
            }
        }
        return QQWindows;
    }
    public static List<String> getAllWindows(){
        List<String> name = new ArrayList<>();
        try
        {
            final List<DesktopWindow> list = WindowUtils.getAllWindows(true);
            for (DesktopWindow dd : list)
            {
                HWND wnd = dd.getHWND();
                Rectangle rr = WindowUtils.getWindowLocationAndSize(wnd);
                if (rr.contains(-32000, -32000)||WindowUtils.getWindowTitle(wnd).equals(""))
                    continue;
                name.add(WindowUtils.getWindowTitle(wnd));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return name;
    }
}