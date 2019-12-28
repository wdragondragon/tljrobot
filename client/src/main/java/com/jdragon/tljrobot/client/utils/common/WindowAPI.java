package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.listener.System.SystemListener;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.util.User32;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowAPI {
    public static List<String> getQQWindows() {
        List<String> allWindows = getAllWindows();
        List<String> QQWindows = new ArrayList<>();
        final ArrayList<HWND> hlist = new ArrayList<HWND>();
        for (int i = 0; i < allWindows.size(); i++) {
            try {
                HWND hWnd = User32.FindWindow("TXGuiFoundation", allWindows.get(i));
                if (hWnd.getValue() > 0) {
                    hlist.add(hWnd);
                    QQWindows.add(allWindows.get(i));
                }
            } catch (IllegalAccessException | NativeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
                WinDef.HWND wnd = dd.getHWND();
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
