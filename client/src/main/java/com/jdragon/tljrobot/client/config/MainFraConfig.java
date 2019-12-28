package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.listener.System.MoveFraListener;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFraConfig {

    private static MainFra mainFra = MainFra.getInstance();
    public static void start(){

//        mainFra.addWindowFocusListener(new StopListener());
        // 设置无边框，用鼠标控制窗体移动
        mainFra.getContentPane().setLayout(null);
        mainFra.setLocationRelativeTo(null);
        mainFra.setUndecorated(true);
        mainFra.setMinimumSize(new Dimension(100, 100));
//		setOpacity(0.95f);
        mainFra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭按钮
        //设置鼠标拖动
        mainFra.addMouseListener(MoveFraListener.getInstance());
        mainFra.addMouseMotionListener(MoveFraListener.getInstance());

        onSystem();
        //设置可见
        mainFra.setVisible(true);
    }
    //创建系统托盘
    private static void onSystem(){
        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
        {
            SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘

            Image image = Toolkit.getDefaultToolkit().getImage(
                    "images\\config_3.png");// 载入图片 图片位置是程序所在的目录
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 创建一个窗体
                    mainFra.setVisible(true);
                }
            };
            // 创建弹出菜单
            PopupMenu popup = new PopupMenu();// 这个是右键才能触发的菜单
            MenuItem defaultItem = new MenuItem("打开");
            defaultItem.addActionListener(listener);
            MenuItem change = new MenuItem("看/跟");
//            change.addActionListener(lookda);

            MenuItem exitItem = new MenuItem("退出");
            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popup.add(defaultItem);
            popup.add(change);
            popup.add(exitItem);
            trayIcon = new TrayIcon(image, "拖拉机跟打器", popup);// 创建trayIcon
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
