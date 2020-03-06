package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.listener.core.MoveFraListener;
import com.jdragon.tljrobot.client.utils.core.IniAccess;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.client.window.dialog.LogonDialog;
import com.jdragon.tljrobot.client.window.dialog.SendArticleDialog;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;
import com.jdragon.tljrobot.client.window.dialog.ThisHistoryDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.jdragon.tljrobot.client.config.LocalConfig.UIBackgroundColor;

public class MainFraConfig {

    private static MainFra mainFra = MainFra.getInstance();
    public static void start(){

//        mainFra.addWindowFocusListener(new StopListener());
        mainFra.getContentPane().setBackground(UIBackgroundColor);
        // 设置无边框，用鼠标控制窗体移动
        mainFra.getContentPane().setLayout(null);
        mainFra.setLocationRelativeTo(null);
//        if(LocalConfig.undecorated)
        mainFra.setUndecorated(LocalConfig.undecorated);
        mainFra.setMinimumSize(new Dimension(100, 100));
//		setOpacity(0.95f);
        mainFra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭按钮
        //设置鼠标拖动
        mainFra.addMouseListener(MoveFraListener.getInstance());
        mainFra.addMouseMotionListener(MoveFraListener.getInstance());
        mainFra.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                IniAccess.writeIni(LocalConfig.iniFilePath);
            }
        });
        mainFra.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                Layout.resetBounds();
            }
        });
        mainFra.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                IniAccess.writeIni(LocalConfig.iniFilePath);
                super.windowClosing(e);
            }
        });
        ImageIcon icon = new ImageIcon("images\\installer_repair_1.png"); // xxx代表图片存放路径，2.png图片名称及格式
        mainFra.setIconImage(icon.getImage());
        onSystem();
        LogonDialog.getInstance();
        SendArticleDialog.getInstance();
        SetDialog.getInstance();
        ThisHistoryDialog.getInstance();
        //设置可见
        mainFra.setVisible(true);
        mainFra.setOpacity(LocalConfig.windowsOpacity);
    }
    //创建系统托盘
    private static void onSystem(){
        TrayIcon trayIcon;
        if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
        {
            SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘

            Image image = Toolkit.getDefaultToolkit().getImage(
                    "images\\config_3.png");// 载入图片 图片位置是程序所在的目录
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 创建一个窗体
                    mainFra.setVisible(true);
                }
            };
            // 创建弹出菜单
            PopupMenu popup = new PopupMenu();// 这个是右键才能触发的菜单
            MenuItem defaultItem = new MenuItem("open");
            defaultItem.addActionListener(listener);
            MenuItem change = new MenuItem("看/跟");
//            change.addActionListener(lookda);

            MenuItem exitItem = new MenuItem("exit");
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popup.add(defaultItem);
//            popup.add(change);
            popup.add(exitItem);
            trayIcon = new TrayIcon(image, "长流跟打器", popup);// 创建trayIcon
            trayIcon.addActionListener(listener);// 给小图标加上监听器，默认的就是监听双击。
            // 如果偶想监听单击啥的 就加mouselistener
            try {
                tray.add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }
}
