package com.jdragon.tljrobot.client.utils.common;

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
        ArrayList<HWND> hlist = new ArrayList<>();
        for (int i = 0; i < allWindows.size(); i++) {
            HWND hWnd = User32.INSTANCE.FindWindow("TXGuiFoundation", allWindows.get(i));
            if (hWnd!=null) {
                hlist.add(hWnd);
                QQWindows.add(allWindows.get(i));
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