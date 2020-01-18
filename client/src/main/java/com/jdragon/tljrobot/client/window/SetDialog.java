package com.jdragon.tljrobot.client.window;

import com.jdragon.tljrobot.client.config.LocalConfig;

import javax.swing.*;
import java.awt.*;

import static com.jdragon.tljrobot.client.utils.core.Layout.addOnBounds;
import static com.jdragon.tljrobot.client.utils.core.Layout.rowAddSpacing;

/**
 * Create by Jdragon on 2020.01.17
 */
public class SetDialog {
    private SetDialog(){}
    private static JDialog setDialog = null;
    public static Dialog getInstance(){
        if(setDialog==null)init();

        return setDialog;
    }
    private static MainFra mainFra = MainFra.getInstance();
    private static JToggleButton progressButton;
    private static JToggleButton tipButton;
    private static JToggleButton lurkButton;
    private static JToggleButton replaceButton;
    private static JToggleButton clearSpaceButton;

    public static JComboBox<String> family;
    public static JTextField typePageCount,fontSize;
    private static JPanel p = new JPanel();
    private static void init(){
        setDialog = new JDialog(mainFra, "登录",
                Dialog.ModalityType.DOCUMENT_MODAL);
        setDialog.setTitle("登录");
        setDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,585,385);
        setDialog.add(p);
        p.setLayout(null);

        progressButton = new JToggleButton("进度条");
        tipButton = new JToggleButton("词语提示");
        lurkButton = new JToggleButton("潜水");
        replaceButton = new JToggleButton("符号替换");
        clearSpaceButton = new JToggleButton("去除空格");


        progressButton.setSelected(LocalConfig.progress);
        tipButton.setSelected(LocalConfig.tip);
        lurkButton.setSelected(LocalConfig.lurk);
        replaceButton.setSelected(LocalConfig.replace);
        clearSpaceButton.setSelected(LocalConfig.clearSpace);


        addOnBounds(p,lurkButton,10,10,50,20);
        addOnBounds(p,clearSpaceButton,rowAddSpacing(lurkButton,10),10,80,20);
        addOnBounds(p,replaceButton,rowAddSpacing(clearSpaceButton,10),10,80,20);
        addOnBounds(p,tipButton,rowAddSpacing(replaceButton,10),10,80,20);
        addOnBounds(p,progressButton,rowAddSpacing(tipButton,10),10,80,20);


        lurkButton.addChangeListener(e-> LocalConfig.lurk=lurkButton.isSelected());
        clearSpaceButton.addChangeListener(e-> LocalConfig.clearSpace=clearSpaceButton.isSelected());
        replaceButton.addChangeListener(e->LocalConfig.replace=replaceButton.isSelected());
        tipButton.addChangeListener(e->LocalConfig.tip=tipButton.isSelected());
        progressButton.addChangeListener(e->LocalConfig.progress=progressButton.isSelected());


        family = new JComboBox<>();
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String [] fontName=ge.getAvailableFontFamilyNames();
        for(int i=0;i<fontName.length;i++){
            family.addItem(fontName[i]);
        }
    }
}
