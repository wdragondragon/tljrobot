package com.jdragon.tljrobot.client.utils.core;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.window.MainFra;

import java.awt.*;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;

public class Layout {
    public static void addOnBounds(Container win, Component component, int x, int y, int width, int height){
        win.add(component);
        component.setBounds(x,y,width,height);
    }
    public static void addSize(int width,int height,Component...components){
        for(Component component:components){
            component.setSize(component.getWidth()+width,component.getHeight()+height);
        }
    }
    public static void addLocation(int x,int y,Component...components){
        for(Component component:components) {
            component.setLocation(component.getX() + x, component.getY() + y);
        }
    }
    public static void addBounds(int x,int y,int width,int height,Component...components) {
        for (Component component : components) {
            component.setBounds(component.getX() + x, component.getY() + y, component.getWidth() + width, component.getHeight() + height);
        }
    }
    public static int rowAddSpacing(Component component,int spacing){
        return component.getX()+component.getWidth()+spacing;
    }
    public static int columnAddSpacing(Component component,int spacing){
        return component.getY()+component.getHeight()+spacing;
    }
    public static void resetBounds(){
        MainFra mainFra = MainFra.getInstance();
        int systemAddWidth = LocalConfig.undecorated?0:15;
        int systemAddHeight = LocalConfig.undecorated?0:40;

        int topButtonWidth = (mainFra.getWidth()-systemAddWidth-(jMenu().getX()+jMenu().getWidth()+40))/4;
        speedButton().setBounds(speedButton().getX(),speedButton().getY(),topButtonWidth,SwingSingleton.speedButton().getHeight());
        keySpeedButton().setBounds(rowAddSpacing(speedButton(),10),speedButton().getY(),topButtonWidth,speedButton().getHeight());
        keyLengthButton().setBounds(rowAddSpacing(keySpeedButton(),10),speedButton().getY(),topButtonWidth,speedButton().getHeight());
        theoreticalCodeLengthButton().setBounds(rowAddSpacing(keyLengthButton(),10),speedButton().getY(),topButtonWidth,SwingSingleton.speedButton().getHeight());
        int baseBottomLabelWidth;
        if(sendArticleLabel().isVisible()){
            baseBottomLabelWidth = (mainFra.getWidth()-systemAddWidth-20)/10;
            sendArticleLabel().setSize(2*baseBottomLabelWidth,sendArticleLabel().getHeight());
        }else{
            baseBottomLabelWidth = (mainFra.getWidth()-systemAddWidth-15)/8;
        }


        closeButton().setBounds(mainFra.getWidth()-systemAddWidth-20,0,20,10);
        maxButton().setBounds(mainFra.getWidth()-systemAddWidth-42,0,20,10);
        minButton().setBounds(mainFra.getWidth()-systemAddWidth-63,0,20,10);
        sizeButton().setBounds(mainFra.getWidth()-systemAddWidth-10,mainFra.getHeight()-10,10,10);
        typingAndWatching().setBounds(0,
                columnAddSpacing(SwingSingleton.speedButton(),5),mainFra.getWidth()-systemAddWidth, mainFra.getHeight()-95-systemAddHeight);
        typingProgress().setBounds(0,
                columnAddSpacing(typingAndWatching(),0),mainFra.getWidth(),10);
        qQNameLabel().setBounds(qQNameLabel().getX(),columnAddSpacing(typingAndWatching(),10),baseBottomLabelWidth*3/2,qQNameLabel().getHeight());
        numberLabel().setBounds(rowAddSpacing(qQNameLabel(),5),qQNameLabel().getY(),baseBottomLabelWidth*3/2,qQNameLabel().getHeight());
        numberRecordLabel().setBounds(rowAddSpacing(numberLabel(),5),qQNameLabel().getY(),3*baseBottomLabelWidth,qQNameLabel().getHeight());
        tipsLabel().setBounds(rowAddSpacing(numberRecordLabel(),5),qQNameLabel().getY(),2*baseBottomLabelWidth,qQNameLabel().getHeight());
        sendArticleLabel().setLocation(rowAddSpacing(tipsLabel(),5),qQNameLabel().getY());
        typingAndWatching().validate();
    }
}
