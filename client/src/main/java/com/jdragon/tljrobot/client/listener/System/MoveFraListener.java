package com.jdragon.tljrobot.client.listener.System;

import com.jdragon.tljrobot.client.window.MainFra;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MoveFraListener implements MouseListener, MouseMotionListener {
    private MoveFraListener(){}
    private static MoveFraListener moveFraListener;
    public static MoveFraListener getInstance(){
        if(moveFraListener==null) moveFraListener = new MoveFraListener();
        return moveFraListener;
    }
    MainFra mainFra = MainFra.getInstance();
    public void mousePressed(MouseEvent e) { // 鼠标按下事件
        mainFra.setPressedPoint(e.getPoint()); // 记录鼠标坐标
    }
    public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
        Point point = e.getPoint();// 获取当前坐标
        Point locationPoint = mainFra.getLocation();// 获取窗体坐标
        int x = locationPoint.x + point.x - mainFra.getPressedPoint().x;// 计算移动后的新坐标
        int y = locationPoint.y + point.y - mainFra.getPressedPoint().y;
        mainFra.setLocation(x, y);// 改变窗体位置
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) // 双击最大化
            if (SystemListener.MaxSign == 0)
                SystemListener.max();
            else
                SystemListener.min();
    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
