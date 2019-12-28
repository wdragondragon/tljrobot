package com.jdragon.tljrobot.client.utils.core;

import com.jdragon.tljrobot.client.factory.SwingSingleton;
import com.jdragon.tljrobot.client.window.MainFra;

import java.awt.*;

import static com.jdragon.tljrobot.client.factory.SwingSingleton.*;

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
        SwingSingleton.CloseButton().setBounds(mainFra.getWidth()-20,0,20,10);
        SwingSingleton.MaxButton().setBounds(mainFra.getWidth()-42,0,20,10);
        SwingSingleton.MinButton().setBounds(mainFra.getWidth()-63,0,20,10);
        SwingSingleton.SizeButton().setBounds(mainFra.getWidth()-10,mainFra.getHeight()-10,10,10);
        TypingAndWatching().setBounds(10,
                columnAddSpacing(SwingSingleton.SpeedButton(),10),mainFra.getWidth()-10, mainFra.getHeight()-100);
        SwingSingleton.TypingProgress().setBounds(10,
                columnAddSpacing(TypingAndWatching(),0),mainFra.getWidth()-10,10);
        SwingSingleton.QQNameLabel().setBounds(TypingAndWatching().getX(),columnAddSpacing(TypingAndWatching(),10),120,40);
        SwingSingleton.NumberLabel().setBounds(rowAddSpacing(QQNameLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
        SwingSingleton.NumberRecordLabel().setBounds(rowAddSpacing(NumberLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
        SwingSingleton.TipsLabel().setBounds(rowAddSpacing(NumberRecordLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
        SwingSingleton.SendArticleLabel().setBounds(rowAddSpacing(TipsLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
    }
}
