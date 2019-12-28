package com.jdragon.tljrobot.client.listener.System;

import com.jdragon.tljrobot.client.factory.SwingSingleton;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SystemListener implements ActionListener, MouseListener, MouseMotionListener {
    private static int x = 0, y = 0;
    private static int width = 0, height = 0;
    public static int MaxSign = 0;
    private static Point pressedPoint;
    private static SystemListener systemListener;
    private SystemListener(){}
    public static SystemListener getInstance(){
        if(systemListener==null) systemListener = new SystemListener();
        return systemListener;
    }
    private static MainFra win = MainFra.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand()=="关"){
//            if(win.one.isVisible()){
//                JOptionPane.showMessageDialog(new JTextArea(),"请先关闭在线对战");return;
//            }
            if(MaxSign==1){
                JOptionPane.showMessageDialog(new JTextArea(),"请先关闭全屏模式");return;
            }
            UIManager.put("OptionPane.yesButtonText", "关闭");
            UIManager.put("OptionPane.noButtonText", "隐藏");
            int n = JOptionPane.showConfirmDialog(null, "要关闭跟打器，还是选择隐藏到托盘", "关闭提示", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
//                try {
//                    readWrite.keep(win);//保存设置
////					SendQQMessage.sendmessage(Login.zhanghao.getText()+"已下线，（上线消息只会发送到拖拉机交流群）");
//                } catch (IOException e1) {System.out.println("保存失败");}
                System.exit(0);
            }
            else{
                win.setVisible(false);
            }
        }else if (e.getActionCommand() == "最大化") {
            if (MaxSign == 0) {
                max();
            } else {
                min();
            }
        } else if (e.getActionCommand().equals("最小化")) {
            System.out.println("最小化");
            win.setExtendedState(JFrame.ICONIFIED);
        }
    }

    public static void max() {
        x = win.getX();
        y = win.getY();
        width = win.getWidth();
        height = win.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(win.getGraphicsConfiguration());
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        win.setBounds(bounds);

        Layout.resetBounds();

        MaxSign = 1;
    }

    public static void min() {
        win.setLocation(x, y);
        win.setSize(width, height);
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        MaxSign = 0;
        Layout.resetBounds();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        win.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)); //改变窗口大小指针
    }

    @Override
    public void mouseExited(MouseEvent e) {
        win.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));    //恢复默认指针
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedPoint = e.getPoint(); //记录鼠标坐标
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        if (MaxSign == 0) {
            Point point = e.getPoint();// 获取当前坐标
            int i =   point.x - pressedPoint.x;// 计算变化坐标
            int j =  point.y - pressedPoint.y;

            Layout.addSize(i,j,win,SwingSingleton.TypingAndWatching());// 改变窗体大小
            Layout.addSize(i,0,SwingSingleton.TypingProgress());
            Layout.addLocation(i,j,SwingSingleton.SizeButton());
            Layout.addLocation(0,j,SwingSingleton.QQNameLabel(),SwingSingleton.NumberRecordLabel(),
                    SwingSingleton.TipsLabel(),SwingSingleton.SendArticleLabel(),
                    SwingSingleton.NumberLabel(),SwingSingleton.TypingProgress());
            Layout.addLocation(i,0,SwingSingleton.CloseButton(),SwingSingleton.MaxButton(),SwingSingleton.MinButton());
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
}
