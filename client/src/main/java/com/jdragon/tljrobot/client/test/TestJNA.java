package com.jdragon.tljrobot.client.test;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinBase.SECURITY_ATTRIBUTES;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * JNA 小试 。
 * @author btpka3@163.com
 */
// https://github.com/twall/jna
// http://today.java.net/article/2009/12/20/simplify-native-code-access-jna#resources
public class TestJNA {

    public static void main(String[] args) {

        // case01();
        // case02();
        // case03();
        case04();
    }

    // 示例 1. 调用 C 函数
    // PS: 一些IO函数，比如 fopen 等，还不知道怎么调用，主要是那个结构体和一些标准常量 比如 stdin 等。
    public static void case01() {
        System.out.println("============================= CASE 1");
        for (int i = 0; i < 10; i++) {
            CLibrary.INSTANCE.printf("%s = %d: \n", "num" + i, i);
        }
        CLibrary.INSTANCE.printf("Hello, World\n");
        System.out.println();
    }

    public interface CLibrary extends Library {
        static CLibrary INSTANCE = (CLibrary) Native.loadLibrary(
                (Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        void printf(String format, Object... args);
    }

    // 示例 2. 打开外部程序（浏览器）
    // PS: 遗留问题，每次都是新浏览器窗口，如何使其在已打开浏览器窗口的新Tab页中打开？
    public static void case02() {
        System.out.println("============================= CASE 2");

        // 打开 IE 浏览器
        // http://msdn.microsoft.com/en-us/library/windows/desktop/ms682425%28v=vs.85%29.aspx
        Kernel32 kernel32 = Kernel32.INSTANCE;
        SECURITY_ATTRIBUTES procSecAttr = new SECURITY_ATTRIBUTES();
        SECURITY_ATTRIBUTES threadSecAttr = new SECURITY_ATTRIBUTES();
        WinBase.PROCESS_INFORMATION.ByReference pi = new WinBase.PROCESS_INFORMATION.ByReference();
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        boolean success = kernel32.CreateProcess(null,
                "explorer.exe http://news.163.com", procSecAttr, threadSecAttr,
                false, new DWORD(0x00000010), null, null, startupInfo, pi);

        // 将使用默认浏览器打开（我这里是火狐浏览器）
        // Shell32.INSTANCE.ShellExecute(null, "open", "http://news.baidu.com",
        // null, null, 9);

        if (!success) {
            System.out.println("打开IE浏览器失败");
        } else {
            System.out.println("打开IE浏览器成功");
        }

        kernel32.CloseHandle(pi.hProcess);
        kernel32.CloseHandle(pi.hThread);

        System.out.println();
    }

    // 示例 3. 打开计算器并修改输入
    public static void case03() {
        System.out.println("============================= CASE 3");

        // 打开计算器
        Kernel32 kernel32 = Kernel32.INSTANCE;
        SECURITY_ATTRIBUTES procSecAttr = new SECURITY_ATTRIBUTES();
        SECURITY_ATTRIBUTES threadSecAttr = new SECURITY_ATTRIBUTES();
        WinBase.PROCESS_INFORMATION.ByReference byRef = new WinBase.PROCESS_INFORMATION.ByReference();
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        startupInfo = new WinBase.STARTUPINFO();
        boolean success = kernel32.CreateProcess(null, "calc.exe", procSecAttr,
                threadSecAttr, false, new DWORD(0x00000010), null, null,
                startupInfo, byRef);
        if (!success) {
            System.out.println("计算器打开失败");
            System.out.println();
            return;
        }

        // 找到计算器的窗口
        // PS：仅仅是为了学习。如果程序是自己打开的，应该使用 startupInfo。
        User32 user32 = User32.INSTANCE;
        int tryTimes = 0;
        boolean found = false;
        HWND h1 = null;
        while (!found && tryTimes < 3) {
            h1 = user32.FindWindow(null, "计算器");
            if (h1 == null) {
                tryTimes++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                found = true;
            }
        }
        System.out.println("tryTimes = " + tryTimes);
        if (h1 == null) {
            System.out.println("找不到计算器");
            System.out.println();
            return;
        }

        // 使计算器窗口置前
        user32.SetForegroundWindow(h1);

        // 找到输入框并设置文本(XP OK，Win7 的计算器已经改变)
        OSVERSIONINFOEX osVerInfo = new OSVERSIONINFOEX();
        boolean setText = kernel32.GetVersionEx(osVerInfo);
        if (setText) {
            int major = osVerInfo.dwMajorVersion.intValue();
            int minor = osVerInfo.dwMinorVersion.intValue();
            if (!(major == 5 && minor == 0) // Windows 2000
                    && !(major == 5 && minor == 1) // Windows XP
                    && !(major == 5 && minor == 1) // Windows 2003 (R2)
            ) {
                setText = false;
            }
        }
        if (setText) {
            MyUser32 myUser32 = MyUser32.INSTANCE;
            HWND h2 = myUser32.FindWindowEx(h1, null, "Edit", null); //
            final int WM_SETTEXT = 0xC;
            myUser32.SendMessage(h2, WM_SETTEXT, new WPARAM(0), "123ABC中文");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 模拟 Esc 按键的 down 与 up，清除设置的不合法输入
            final int VK_ESCAPE = 0x1B;
            WPARAM wParam = new WPARAM(VK_ESCAPE);
            int repeatCount = 1;
            int scanCode = 0;
            int itendedKey = 0;
            int contextCode = 0;
            int preKeyState = 0;
            int transitionState = 0;
            LPARAM lParam = new LPARAM(repeatCount | scanCode | itendedKey
                    | contextCode | contextCode | preKeyState | transitionState);
            user32.PostMessage(h1, User32.WM_KEYDOWN, wParam, lParam);

            repeatCount = 1;
            scanCode = 0;
            itendedKey = 0;
            contextCode = 0;
            preKeyState = 1 << 31;
            transitionState = 1 << 31;
            user32.PostMessage(h1, User32.WM_KEYUP, wParam, lParam);

            try {
                System.out.println("文字应当已经被清除，请确认。");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 调用 PostMessage, 计算 1+2+3+4+5+6+7+8+9=
        // FIXME : 区别 SendInput SendMessage PostMessage
        for (int i = 0; i < 10; i++) {
            user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('0' + i),
                    new LPARAM(0));
            if (i < 9) {
                user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('+'),
                        new LPARAM(0));
            } else {
                user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('='),
                        new LPARAM(0));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public interface MyUser32 extends StdCallLibrary, WinUser {
        static MyUser32 INSTANCE = (MyUser32) Native.loadLibrary("user32",
                MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter,
                          String lpszClass, String lpszWindow);

        // 注意：最后一个参数的类型，该函数在Java里可以声明多个类型。
        LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, String lParam);

        LPARAM GetMessageExtraInfo();
    }

    public static HHOOK hHook;
    public static HOOKPROC lpfn;
    public static volatile boolean quit = false;

    /**
     * 全局键盘钩子 http://www.cnblogs.com/chuncn/archive/2009/01/12/1374109.html
     *
     * FIXME 如何做到线程键盘钩子？
     */
    public static void case04() {

        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        lpfn = new LowLevelKeyboardProc() {
            int count = 1;

            @Override
            public LRESULT callback(int nCode, WPARAM wParam,
                                    KBDLLHOOKSTRUCT keyInfo) {
                System.out.println("nCode =" + nCode + ", wParam =" + wParam
                        + ", vkCode=" + keyInfo.vkCode);
                count++;
                if (count > 10) {
                    quit = true;
                }
                return User32.INSTANCE.CallNextHookEx(hHook, nCode, wParam,null);
//                        keyInfo.getPointer());
            }
        };

        // 如果是全局钩子，则线程ID为0
        int threadId = 0;
        // int threadId =
        // user32.GetWindowThreadProcessId(user32.FindWindow(null, "无标题 - 记事本"),
        // null);
        System.out.println("threadId = "
                + Integer.toHexString(threadId).toUpperCase());
        int idHook = User32.WH_KEYBOARD_LL;
        hHook = User32.INSTANCE.SetWindowsHookEx(idHook, lpfn, hMod, threadId);

        if (hHook == null) {
            System.out.println("键盘钩子安装失败，error = "
                    + Kernel32.INSTANCE.GetLastError());
            return;
        }
        User32.MSG msg = new User32.MSG();
        while (!quit) {
            User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (User32.INSTANCE.UnhookWindowsHookEx(hHook)) {
            System.out.println("Unhooked");
        }
    }
}