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
import java.util.ArrayList;

import static com.jdragon.tljrobot.client.utils.core.Layout.*;

/**
 * Create by Jdragon on 2020.01.17
 */
public class SetDialog{
    private SetDialog(){}
    private static JDialog setDialog = null;
    public static Dialog getInstance(){
        if(setDialog==null)init();
        setDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,535,300);
        return setDialog;
    }
    private static MainFra mainFra = MainFra.getInstance();
    private static JTabbedPane tabbedPane = new JTabbedPane();

    private static JPanel baseSetPanel = new JPanel();
    private static JToggleButton progressButton,tipButton,lurkButton,replaceButton,clearSpaceButton;
    public static JTextField typePageCountText, fontSizeText,readyFont;
    public static JComboBox<String> family;

    private static JPanel sendAchSetPanel = new JPanel();
    private static JToggleButton shortCodesNumButton,articleLengthButton,deleteTextNumberButton,deleteNumberButton,
            mistakeButton,keyNumberButton,repeatButton,keyAccuracyButton,keyMethodButton,wordRateButton,repeatRateButton,
            changLiuVersionButton,systemVersionButton,checkCodeButton,personalTagButton,typeWritingButton;
    private static ArrayList<JToggleButton> achJToggleButtonList = new ArrayList<>();
    private static JTextField personalTagText,typeWritingText;
    private static JButton achSelectAll,reverseSelect,achCancelAll;
    private static void init(){
        setDialog = new JDialog(mainFra, "设置",
                Dialog.ModalityType.DOCUMENT_MODAL);
        setDialog.setTitle("设置");
        setDialog.add(tabbedPane);
        addBaseSet();
        addSendAchSet();
    }
    private static void addSendAchSet(){
        sendAchSetPanel.setLayout(null);
        achJToggleButtonList.add(shortCodesNumButton = new JToggleButton("标顶码长"));
        achJToggleButtonList.add(articleLengthButton = new JToggleButton("文章长度"));
        achJToggleButtonList.add(deleteNumberButton = new JToggleButton("退格数量"));
        achJToggleButtonList.add(deleteTextNumberButton = new JToggleButton("回改数量"));
        achJToggleButtonList.add(mistakeButton = new JToggleButton("错字数量"));

        achJToggleButtonList.add(keyNumberButton = new JToggleButton("击键数量"));
        achJToggleButtonList.add(repeatButton = new JToggleButton("选重数量"));
        achJToggleButtonList.add(keyAccuracyButton = new JToggleButton("键准"));
        achJToggleButtonList.add(keyMethodButton = new JToggleButton("键法"));
        achJToggleButtonList.add(wordRateButton = new JToggleButton("打词率"));

        achJToggleButtonList.add(repeatRateButton = new JToggleButton("选重率"));
        achJToggleButtonList.add(changLiuVersionButton = new JToggleButton("版本号"));
        achJToggleButtonList.add(systemVersionButton = new JToggleButton("系统类型"));
        achJToggleButtonList.add(checkCodeButton = new JToggleButton("校验码"));
        achJToggleButtonList.add(personalTagButton = new JToggleButton("个性签名"));

        achJToggleButtonList.add(typeWritingButton = new JToggleButton("输入法"));
        achSelectAll = new JButton("全选");
        reverseSelect = new JButton("反选");
        achCancelAll = new JButton("全不选");

        personalTagText = new JTextField(LocalConfig.personalTag);
        typeWritingText = new JTextField(LocalConfig.typeWriting);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                if (personalTagText.getText().length()>20||typeWritingText.getText().length()>10) {
                    e.consume();
                    JOptionPane.showMessageDialog(setDialog,"输入过长");
                }
                else{
                    LocalConfig.personalTag = personalTagText.getText();
                    LocalConfig.typeWriting = typeWritingText.getText();
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }
        };
        personalTagText.addKeyListener(keyListener);
        typeWritingText.addKeyListener(keyListener);
        /**
         * 初始化设置
         */
        shortCodesNumButton.setSelected(LocalConfig.shortCodesNum);
        articleLengthButton.setSelected(LocalConfig.articleLength);
        deleteNumberButton.setSelected(LocalConfig.deleteNumber);
        deleteTextNumberButton.setSelected(LocalConfig.deleteTextNumber);
        mistakeButton.setSelected(LocalConfig.mistake);

        keyNumberButton.setSelected(LocalConfig.keyNumber);
        repeatButton.setSelected(LocalConfig.repeat);
        keyAccuracyButton.setSelected(LocalConfig.keyAccuracy);
        keyMethodButton.setSelected(LocalConfig.keyMethod);
        wordRateButton.setSelected(LocalConfig.wordRate);

        repeatRateButton.setSelected(LocalConfig.repeatRate);
        changLiuVersionButton.setSelected(LocalConfig.changLiuVersion);
        systemVersionButton.setSelected(LocalConfig.systemVersion);
        checkCodeButton.setSelected(LocalConfig.checkCode);
        personalTagButton.setSelected(LocalConfig.personalTagSign);

        typeWritingButton.setSelected(LocalConfig.typeWritingSign);

        /**
         * 初始化位置
         */
        addOnBounds(sendAchSetPanel,shortCodesNumButton,40,10,80,20);
        addOnBounds(sendAchSetPanel,articleLengthButton,rowAddSpacing(shortCodesNumButton,10),shortCodesNumButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,deleteNumberButton,rowAddSpacing(articleLengthButton,10),articleLengthButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,deleteTextNumberButton,rowAddSpacing(deleteNumberButton,10),deleteNumberButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,mistakeButton,rowAddSpacing(deleteTextNumberButton,10),deleteTextNumberButton.getY(),80,20);

        addOnBounds(sendAchSetPanel,keyNumberButton,shortCodesNumButton.getX(),columnAddSpacing(shortCodesNumButton,10),80,20);
        addOnBounds(sendAchSetPanel,repeatButton,rowAddSpacing(keyNumberButton,10),keyNumberButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,keyAccuracyButton,rowAddSpacing(repeatButton,10),repeatButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,keyMethodButton,rowAddSpacing(keyAccuracyButton,10),keyAccuracyButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,wordRateButton,rowAddSpacing(keyMethodButton,10),keyMethodButton.getY(),80,20);

        addOnBounds(sendAchSetPanel,repeatRateButton,keyNumberButton.getX(),columnAddSpacing(keyNumberButton,10),80,20);
        addOnBounds(sendAchSetPanel,changLiuVersionButton,rowAddSpacing(repeatRateButton,10),repeatRateButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,systemVersionButton,rowAddSpacing(changLiuVersionButton,10),changLiuVersionButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,checkCodeButton,rowAddSpacing(systemVersionButton,10),systemVersionButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,personalTagButton,rowAddSpacing(checkCodeButton,10),checkCodeButton.getY(),80,20);

        addOnBounds(sendAchSetPanel,typeWritingButton,repeatRateButton.getX(),columnAddSpacing(repeatRateButton,10),80,20);
        addOnBounds(sendAchSetPanel,achSelectAll,rowAddSpacing(typeWritingButton,10),typeWritingButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,reverseSelect,rowAddSpacing(achSelectAll,10),achSelectAll.getY(),80,20);
        addOnBounds(sendAchSetPanel,achCancelAll,rowAddSpacing(reverseSelect,10),reverseSelect.getY(),80,20);

        addOnBounds(sendAchSetPanel,typeWritingText,typeWritingButton.getX(),columnAddSpacing(typeWritingButton,10),215,30);
        addOnBounds(sendAchSetPanel,personalTagText,rowAddSpacing(typeWritingText,10),typeWritingText.getY(),215,30);
        /**
         * 添加监听
         */
        shortCodesNumButton.addChangeListener(e->LocalConfig.shortCodesNum=shortCodesNumButton.isSelected());
        articleLengthButton.addChangeListener(e->LocalConfig.articleLength=articleLengthButton.isSelected());
        deleteNumberButton.addChangeListener(e->LocalConfig.deleteNumber=deleteNumberButton.isSelected());
        deleteTextNumberButton.addChangeListener(e->LocalConfig.deleteTextNumber=deleteTextNumberButton.isSelected());
        mistakeButton.addChangeListener(e->LocalConfig.mistake=mistakeButton.isSelected());

        keyNumberButton.addChangeListener(e->LocalConfig.keyNumber=keyNumberButton.isSelected());
        repeatButton.addChangeListener(e->LocalConfig.repeat=repeatButton.isSelected());
        keyAccuracyButton.addChangeListener(e->LocalConfig.keyAccuracy=keyAccuracyButton.isSelected());
        keyMethodButton.addChangeListener(e->LocalConfig.keyMethod=keyMethodButton.isSelected());
        wordRateButton.addChangeListener(e->LocalConfig.wordRate=wordRateButton.isSelected());

        repeatRateButton.addChangeListener(e->LocalConfig.repeatRate=repeatRateButton.isSelected());
        changLiuVersionButton.addChangeListener(e->LocalConfig.changLiuVersion=changLiuVersionButton.isSelected());
        systemVersionButton.addChangeListener(e->LocalConfig.systemVersion=systemVersionButton.isSelected());
        checkCodeButton.addChangeListener(e->LocalConfig.checkCode=checkCodeButton.isSelected());
        personalTagButton.addChangeListener(e->LocalConfig.personalTagSign=personalTagButton.isSelected());

        typeWritingButton.addChangeListener(e->LocalConfig.typeWritingSign=typeWritingButton.isSelected());

        achSelectAll.addActionListener(e->{
            for(JToggleButton jToggleButton:achJToggleButtonList){
                jToggleButton.setSelected(true);
            }
        });
        achCancelAll.addActionListener(e->{
            for(JToggleButton jToggleButton:achJToggleButtonList){
                jToggleButton.setSelected(false);
            }
        });
        reverseSelect.addActionListener(e->{
            for (JToggleButton jToggleButton:achJToggleButtonList){
                jToggleButton.setSelected(!jToggleButton.isSelected());
            }
        });
        tabbedPane.add("成绩设置",sendAchSetPanel);
    }
    private static void addBaseSet(){
        baseSetPanel.setLayout(null);

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


        addOnBounds(baseSetPanel,lurkButton,10,10,50,20);
        addOnBounds(baseSetPanel,clearSpaceButton,rowAddSpacing(lurkButton,10),10,80,20);
        addOnBounds(baseSetPanel,replaceButton,rowAddSpacing(clearSpaceButton,10),10,80,20);
        addOnBounds(baseSetPanel,tipButton,rowAddSpacing(replaceButton,10),10,80,20);
        addOnBounds(baseSetPanel,progressButton,rowAddSpacing(tipButton,10),10,80,20);


        lurkButton.addChangeListener(e-> LocalConfig.lurk=lurkButton.isSelected());
        clearSpaceButton.addChangeListener(e-> LocalConfig.clearSpace=clearSpaceButton.isSelected());
        replaceButton.addChangeListener(e->LocalConfig.replace=replaceButton.isSelected());
        tipButton.addChangeListener(e->LocalConfig.tip=tipButton.isSelected());
        progressButton.addChangeListener(e->LocalConfig.progress=progressButton.isSelected());


        JButton RightcolorSet = new JButton("打对字颜色");
        RightcolorSet.setBounds(10,40,100,30);
        baseSetPanel.add(RightcolorSet);

        JButton MistakecolorSet = new JButton("打错字颜色");
        MistakecolorSet.setBounds(RightcolorSet.getX()+RightcolorSet.getWidth()+10,RightcolorSet.getY(),100,30);
        baseSetPanel.add(MistakecolorSet);

        JButton WenbenBackgroundSet = new JButton("文本框背景颜色");
        WenbenBackgroundSet.setBounds(MistakecolorSet.getX()+MistakecolorSet.getWidth()+10,RightcolorSet.getY(),140,30);
        baseSetPanel.add(WenbenBackgroundSet);

        JButton qmccolorset = new JButton("全码词颜色");
        qmccolorset.setBounds(WenbenBackgroundSet.getX()+WenbenBackgroundSet.getWidth()+10,RightcolorSet.getY(),140,30);
        baseSetPanel.add(qmccolorset);

        JButton BackgroundSet = new JButton("整体界面颜色");
        BackgroundSet.setBounds(RightcolorSet.getX(),RightcolorSet.getY()+RightcolorSet.getHeight()+10,140,30);
        baseSetPanel.add(BackgroundSet);

        JButton DaziBackgroundSet = new JButton("打字框背景颜色");
        DaziBackgroundSet.setBounds(BackgroundSet.getX()+BackgroundSet.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,140,30);
        baseSetPanel.add(DaziBackgroundSet);

        JButton emccolorset = new JButton("二码词颜色");
        emccolorset.setBounds(DaziBackgroundSet.getX()+DaziBackgroundSet.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,100,30);
        baseSetPanel.add(emccolorset);

        JButton smccolorset = new JButton("三码词颜色");
        smccolorset.setBounds(emccolorset.getX()+emccolorset.getWidth()+10,RightcolorSet.getHeight()+RightcolorSet.getY()+10,100,30);
        baseSetPanel.add(smccolorset);

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
        baseSetPanel.add(fontSizeText);

        JButton changeFontSize = new JButton("保存字体大小");
        changeFontSize.setBounds(fontSizeText.getX()+ fontSizeText.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,90,30);
        baseSetPanel.add(changeFontSize);

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
        baseSetPanel.add(typePageCountText);

        JButton spliteButton = new JButton("保存页字数");
        spliteButton.setBounds(typePageCountText.getX()+ typePageCountText.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,90,30);
        baseSetPanel.add(spliteButton);

        JButton codeTableButton = new JButton("全码表选择");
        codeTableButton.setBounds(spliteButton.getX()+spliteButton.getWidth()+10,BackgroundSet.getY()+BackgroundSet.getHeight()+10,100,30);
        baseSetPanel.add(codeTableButton);

        family = new JComboBox<String>();
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String [] fontName=ge.getAvailableFontFamilyNames();
        for (String s : fontName) {
            family.addItem(s);
        }
        family.setBounds(RightcolorSet.getX(), fontSizeText.getY()+ fontSizeText.getHeight()+10,120,30);
        baseSetPanel.add(family);

        JButton familyChange = new JButton("修改字型");
        familyChange.setBounds(family.getX()+family.getWidth()+10, fontSizeText.getY()+ fontSizeText.getHeight()+10,90,30);
        baseSetPanel.add(familyChange);

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
        baseSetPanel.add(readyFont);

        tabbedPane.add("基本设置", baseSetPanel);

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
