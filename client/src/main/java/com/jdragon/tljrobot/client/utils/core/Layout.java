package com.jdragon.tljrobot.client.utils.core;

import com.jdragon.tljrobot.client.component.SwingSingleton;
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
        SwingSingleton.closeButton().setBounds(mainFra.getWidth()-20,0,20,10);
        SwingSingleton.maxButton().setBounds(mainFra.getWidth()-42,0,20,10);
        SwingSingleton.minButton().setBounds(mainFra.getWidth()-63,0,20,10);
        SwingSingleton.sizeButton().setBounds(mainFra.getWidth()-10,mainFra.getHeight()-10,10,10);
        typingAndWatching().setBounds(10,
                columnAddSpacing(SwingSingleton.speedButton(),10),mainFra.getWidth()-10, mainFra.getHeight()-100);
        SwingSingleton.typingProgress().setBounds(10,
                columnAddSpacing(typingAndWatching(),0),mainFra.getWidth()-10,10);
        SwingSingleton.qQNameLabel().setBounds(typingAndWatching().getX(),columnAddSpacing(typingAndWatching(),10),120,40);
        SwingSingleton.numberLabel().setBounds(rowAddSpacing(qQNameLabel(),10),columnAddSpacing(typingAndWatching(),10),120,40);
        SwingSingleton.numberRecordLabel().setBounds(rowAddSpacing(numberLabel(),10),columnAddSpacing(typingAndWatching(),10),120,40);
        SwingSingleton.tipsLabel().setBounds(rowAddSpacing(numberRecordLabel(),10),columnAddSpacing(typingAndWatching(),10),120,40);
        SwingSingleton.sendArticleLabel().setBounds(rowAddSpacing(tipsLabel(),10),columnAddSpacing(typingAndWatching(),10),120,40);
    }
}
