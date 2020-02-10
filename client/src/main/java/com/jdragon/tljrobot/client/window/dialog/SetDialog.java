package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.JTextPaneFontConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.listener.common.SetBackgroundListener;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import com.jdragon.tljrobot.client.utils.common.ChooseFile;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.jdragon.tljrobot.client.utils.core.Layout.addOnBounds;
import static com.jdragon.tljrobot.client.utils.core.Layout.rowAddSpacing;

/**
 * Create by Jdragon on 2020.01.17
 */
public class SetDialog{
    private SetDialog(){}
    private static JDialog setDialog = null;
    public static Dialog getInstance(){
        if(setDialog==null)init();
        setDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,535,250);
        return setDialog;
    }
    private static MainFra mainFra = MainFra.getInstance();
    private static JToggleButton progressButton;
    private static JToggleButton tipButton;
    private static JToggleButton lurkButton;
    private static JToggleButton replaceButton;
    private static JToggleButton clearSpaceButton;

    public static JTextField typePageCountText, fontSizeText,readyFont;

    public static JComboBox<String> family;
    private static JPanel p = new JPanel();
    private static void init(){
        setDialog = new JDialog(mainFra, "设置",
                Dialog.ModalityType.DOCUMENT_MODAL);
        setDialog.setTitle("设置");
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


        JButton RightcolorSet = new JButton("打对字颜色");
        RightcolorSet.setBounds(10,40,100,30);
        p.add(RightcolorSet);

        JButton MistakecolorSet = new JButton("打错字颜色");
        MistakecolorSet.setBounds(RightcolorSet.getX()+RightcolorSet.getWidth()+10,RightcolorSet.getY(),100,30);
        p.add(MistakecolorSet);

        JButton WenbenBackgroundSet = new JButton("文本框背景颜色");
        WenbenBackgroundSet.setBounds(MistakecolorSet.getX()+MistakecolorSet.getWidth()+10,RightcolorSet.getY(),140,30);
        p.add(WenbenBackgroundSet);

        JButton qmccolorset = new JButton("全码词颜色");
        qmccolorset.setBounds(WenbenBackgroundSet.getX()+WenbenBackgroundSet.getWidth()+10,RightcolorSet.getY(),140,30);
        p.add(qmccolorset);

        JButton BackgroundSet = new JButton("整体界面颜色");
        BackgroundSet.setBounds(RightcolorSet.getX(),RightcolorSet.getY()+RightcolorSet.getHeight()+10,140,30);
        p.add(BackgroundSet);

        JButton DaziBackgroundSet = new JButton("打字框背景颜色");
        DaziBackgroundSet.setBounds(BackgroundSet.getX()+BackgroundSet.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,140,30);
        p.add(DaziBackgroundSet);

        JButton emccolorset = new JButton("二码词颜色");
        emccolorset.setBounds(DaziBackgroundSet.getX()+DaziBackgroundSet.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,100,30);
        p.add(emccolorset);

        JButton smccolorset = new JButton("三码词颜色");
        smccolorset.setBounds(emccolorset.getX()+emccolorset.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,100,30);
        p.add(smccolorset);

        fontSizeText = new JTextField();
        fontSizeText.setBounds(BackgroundSet.getX(),BackgroundSet.getY()+BackgroundSet.getHeight()+10,70,30);
        fontSizeText.addKeyListener(		//只能输入数字
                new KeyListener(){
                    @Override
                    public void keyPressed(KeyEvent arg0) {}
                    @Override
                    public void keyReleased(KeyEvent arg0) {}
                    @Override
                    public void keyTyped(KeyEvent e) {
                        // TODO Auto-generated method stub
                        int keyChar = e.getKeyChar();
                        if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){}
                        else e.consume();
                    }
                }
        );
        fontSizeText.setText(String.valueOf(LocalConfig.fontSize));
        p.add(fontSizeText);

        JButton changeFontSize = new JButton("保存字体大小");
        changeFontSize.setBounds(fontSizeText.getX()+ fontSizeText.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,90,30);
        p.add(changeFontSize);

        typePageCountText = new JTextField();
        typePageCountText.setBounds(changeFontSize.getX()+changeFontSize.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,70,30);
        typePageCountText.addKeyListener(		//只能输入数字
                new KeyListener(){
                    @Override
                    public void keyPressed(KeyEvent arg0) {}
                    @Override
                    public void keyReleased(KeyEvent arg0) {}
                    @Override
                    public void keyTyped(KeyEvent e) {
                        // TODO Auto-generated method stub
                        int keyChar = e.getKeyChar();
                        if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){}
                        else e.consume();
                    }
                }
        );
        typePageCountText.setText(String.valueOf(LocalConfig.typePageCount));
        p.add(typePageCountText);

        JButton spliteButton = new JButton("保存页字数");
        spliteButton.setBounds(typePageCountText.getX()+ typePageCountText.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,90,30);
        p.add(spliteButton);

        JButton codeTableButton = new JButton("全码表选择");
        codeTableButton.setBounds(spliteButton.getX()+spliteButton.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,100,30);
        p.add(codeTableButton);

        family = new JComboBox<String>();
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String [] fontName=ge.getAvailableFontFamilyNames();
        for (String s : fontName) {
            family.addItem(s);
        }
        family.setBounds(RightcolorSet.getX(), fontSizeText.getY()+ fontSizeText.getHeight()+10,120,30);
        p.add(family);

        JButton familyChange = new JButton("修改字型");
        familyChange.setBounds(family.getX()+family.getWidth()+10, fontSizeText.getY()+ fontSizeText.getHeight()+10,90,30);
        p.add(familyChange);

        readyFont = new JTextField("0");
        readyFont.setBounds(familyChange.getX()+familyChange.getWidth()+10, fontSizeText.getY()+ fontSizeText.getHeight()+10,90,30);
        readyFont.addKeyListener(		//只能输入数字
                new KeyListener(){
                    @Override
                    public void keyPressed(KeyEvent arg0) {}
                    @Override
                    public void keyReleased(KeyEvent arg0) {}
                    @Override
                    public void keyTyped(KeyEvent e) {
                        // TODO Auto-generated method stub
                        int keyChar = e.getKeyChar();
                        if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){}
                        else e.consume();
                    }
                }
        );
        p.add(readyFont);

        SetBackgroundListener setbackgroundListener = new SetBackgroundListener();

        WenbenBackgroundSet.addActionListener(setbackgroundListener);
        RightcolorSet.addActionListener(setbackgroundListener);
        MistakecolorSet.addActionListener(setbackgroundListener);
        DaziBackgroundSet.addActionListener(setbackgroundListener);
        BackgroundSet.addActionListener(setbackgroundListener);
        qmccolorset.addActionListener(setbackgroundListener);
        emccolorset.addActionListener(setbackgroundListener);
        smccolorset.addActionListener(setbackgroundListener);

        spliteButton.addActionListener(e-> LocalConfig.typePageCount = Integer.parseInt(typePageCountText.getText()));
        changeFontSize.addActionListener(e->{
            LocalConfig.fontSize = Integer.parseInt(fontSizeText.getText());
            JTextPaneFontConfig.start();
            TypingListener.getInstance().changeFontColor();
            SwingSingleton.TypingText().setFont(new Font(LocalConfig.family, Font.PLAIN, LocalConfig.fontSize));
        });
        codeTableButton.addActionListener(new ChooseFile());
        familyChange.addActionListener(e-> {
            LocalConfig.family = family.getSelectedItem().toString();
            JTextPaneFontConfig.start();
            TypingListener.getInstance().changeFontColor();
        });
    }
}
