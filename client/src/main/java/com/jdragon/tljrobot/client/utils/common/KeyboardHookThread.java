package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.window.MainFra;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Jdragon on 2020.02.07
 */
@Slf4j
public class KeyboardHookThread implements Runnable {
    private WinUser.HHOOK hhk;
    int preButton;
    //钩子回调函数
    private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
        @Override
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
            // 输出按键值和按键时间
            if (nCode >= 0&&event.flags==0) {
                preButton = event.vkCode;
                WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null, MainFra.getInstance().getTitle());
//                        User32.INSTANCE.FindWindow("TXGuiFoundation", SwingSingleton.QQNameLabel().getText());
                if(preButton==114){
                    System.out.println(hWnd);
                    User32.INSTANCE.SetForegroundWindow(hWnd);
                }
//                WinDef.RECT rect = new WinDef.RECT();
//                User32.INSTANCE.GetWindowRect(hWnd,rect);
//                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                log.debug("hook：{}，code：{}", (char)event.vkCode,event.vkCode);
                // 按下ESC退出
                if (event.vkCode == 27) {
                    KeyboardHookThread.this.setHookOff();
                }
            }
            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
        }
    };//MyBlog @See http://blog.csdn.net/shenpibaipao

    @Override
    public void run() {
        setHookOn();
    }

    // 安装钩子
    public void setHookOn() {
        System.out.println("Hook On!");

        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        hhk = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardProc, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                setHookOff();
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }

    // 移除钩子并退出
    public void setHookOff() {
//        System.out.println("Hook Off!");
//        User32.INSTANCE.UnhookWindowsHookEx(hhk);
//        System.exit(0);
    }
    public static void main(String []args){
        KeyboardHookThread kbhook = new KeyboardHookThread();
        new Thread(kbhook).start();
    }
}