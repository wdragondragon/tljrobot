package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.JTextPaneFontConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.listener.common.SetBackgroundListener;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import com.jdragon.tljrobot.client.utils.common.BetterTypingSingleton;
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
    public static JDialog getInstance(){
        if(setDialog==null) {
            init();
        }
        setDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,535,330);
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
            changLiuVersionButton,systemVersionButton,checkCodeButton,personalTagButton,typeWritingButton,ctrlSendAchToQQ;
    private static ArrayList<JToggleButton> achJToggleButtonList = new ArrayList<>();
    private static JTextField personalTagText,typeWritingText;
    private static JButton achSelectAll,reverseSelect,achCancelAll;

    private static JPanel getArticleSetPanel = new JPanel();
    private static JToggleButton getArticleOnNet,mouseGetArticle;
    private static ArrayList<JToggleButton> getArticleJToggleButtonList = new ArrayList<>();

    public static JPanel windowsSetPanel = new JPanel();
    private static JLabel opacityLabel,themeSelectLabel;
    private static JSlider windowsOpacitySlider;
    private static JComboBox<String> windowsThemeSelect;
    private static JToggleButton undecoratedButton;

    public static JToggleButton getTipsButton(){
        return tipButton;
    }
    private static void init(){
        setDialog = new JDialog(mainFra, "设置",
                Dialog.ModalityType.DOCUMENT_MODAL);
        setDialog.setTitle("设置");
        setDialog.add(tabbedPane);
        addBaseSet();
        addSendAchSet();
        addGetArticleSet();
        addWindowsSetPanel();
    }
    private static void addWindowsSetPanel(){
        windowsSetPanel.setLayout(null);

        opacityLabel = new JLabel("窗体透明");
        themeSelectLabel = new JLabel("主题选择");

        windowsOpacitySlider = new JSlider(0, 100, (int)(LocalConfig.windowsOpacity*100));
        windowsThemeSelect = new JComboBox<>();
        windowsThemeSelect.addItem("长流默认");
        windowsThemeSelect.addItem("系统默认");
        windowsThemeSelect.addItem("LittleLuck蓝白");
        windowsThemeSelect.addItem("Substance黑白");
        windowsThemeSelect.addItem("Nimbus灰白");
        windowsThemeSelect.setSelectedItem(LocalConfig.windowsTheme);

        undecoratedButton = new JToggleButton("去边框");
        undecoratedButton.setSelected(LocalConfig.undecorated);


        windowsOpacitySlider.addChangeListener(e->mainFra.setOpacity(LocalConfig.windowsOpacity = windowsOpacitySlider.getValue()/100f));
        windowsThemeSelect.addItemListener(e->LocalConfig.windowsTheme = windowsThemeSelect.getSelectedItem().toString());
        undecoratedButton.addChangeListener(e->LocalConfig.undecorated=undecoratedButton.isSelected());

        addOnBounds(windowsSetPanel,opacityLabel,40,10,50,30);
        addOnBounds(windowsSetPanel,windowsOpacitySlider,rowAddSpacing(opacityLabel,10),opacityLabel.getY(),200,30);
        addOnBounds(windowsSetPanel,themeSelectLabel,opacityLabel.getX(),columnAddSpacing(opacityLabel,10),50,30);
        addOnBounds(windowsSetPanel, windowsThemeSelect,rowAddSpacing(themeSelectLabel,10),themeSelectLabel.getY(),120,30);
        addOnBounds(windowsSetPanel,undecoratedButton,rowAddSpacing(windowsThemeSelect,10),windowsThemeSelect.getY(),60,30);

        tabbedPane.add("窗体设置",windowsSetPanel);
    }
    private static void addGetArticleSet(){
        getArticleSetPanel.setLayout(null);
        getArticleJToggleButtonList.add(getArticleOnNet = new JToggleButton("网络模式"));
        getArticleJToggleButtonList.add(mouseGetArticle = new JToggleButton("鼠标载文"));
        for (JToggleButton jToggleButton:getArticleJToggleButtonList){
            jToggleButton.setFont(SwingSingleton.tipFont());
        }
        getArticleOnNet.addChangeListener(e->LocalConfig.getArticleOnNet=getArticleOnNet.isSelected());
        mouseGetArticle.addChangeListener(e->LocalConfig.mouseGetArticle=mouseGetArticle.isSelected());

        addOnBounds(getArticleSetPanel,getArticleOnNet,40,10,80,20);
        addOnBounds(getArticleSetPanel,mouseGetArticle,rowAddSpacing(getArticleOnNet,10),getArticleOnNet.getY(),80,20);

        getArticleOnNet.setSelected(LocalConfig.getArticleOnNet);
        mouseGetArticle.setSelected(LocalConfig.mouseGetArticle);

        tabbedPane.add("载文设置",getArticleSetPanel);
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
        achJToggleButtonList.add(ctrlSendAchToQQ = new JToggleButton("ctrl发送"));
        achSelectAll = new JButton("全选");
        reverseSelect = new JButton("反选");
        achCancelAll = new JButton("全不选");

        for(JToggleButton jToggleButton:achJToggleButtonList){
            jToggleButton.setFont(SwingSingleton.tipFont());
        }
        achCancelAll.setFont(SwingSingleton.tipFont());
        reverseSelect.setFont(SwingSingleton.tipFont());
        achCancelAll.setFont(SwingSingleton.tipFont());


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
        ctrlSendAchToQQ.setSelected(LocalConfig.ctrlSendAchToQQ);
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
        addOnBounds(sendAchSetPanel,ctrlSendAchToQQ,rowAddSpacing(typeWritingButton,10),typeWritingButton.getY(),80,20);
        addOnBounds(sendAchSetPanel,achSelectAll,rowAddSpacing(ctrlSendAchToQQ,10),ctrlSendAchToQQ.getY(),80,20);
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
        ctrlSendAchToQQ.addChangeListener(e->LocalConfig.ctrlSendAchToQQ=ctrlSendAchToQQ.isSelected());

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
        tabbedPane.add("发送设置",sendAchSetPanel);
    }
    private static void addBaseSet(){
        baseSetPanel.setLayout(null);

        progressButton = new JToggleButton("进度条");
        tipButton = new JToggleButton("词语提示");
        lurkButton = new JToggleButton("潜水");
        replaceButton = new JToggleButton("符号替换");
        clearSpaceButton = new JToggleButton("去除空格");

        progressButton.setFont(SwingSingleton.tipFont());
        tipButton.setFont(SwingSingleton.tipFont());
        lurkButton.setFont(SwingSingleton.tipFont());
        replaceButton.setFont(SwingSingleton.tipFont());
        clearSpaceButton.setFont(SwingSingleton.tipFont());

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
        tipButton.addChangeListener(e->{
            LocalConfig.tip=tipButton.isSelected();
            TypingListener.getInstance().changeFontColor();
            TypingListener.getInstance().changePosition();
        });
        progressButton.addChangeListener(e->LocalConfig.progress=progressButton.isSelected());


        JButton rightcolorSet = new JButton("打对字颜色");
        rightcolorSet.setFont(SwingSingleton.tipFont());
        rightcolorSet.setBounds(10,40,100,30);
        baseSetPanel.add(rightcolorSet);

        JButton mistakecolorSet = new JButton("打错字颜色");
        mistakecolorSet.setFont(SwingSingleton.tipFont());
        mistakecolorSet.setBounds(rightcolorSet.getX()+rightcolorSet.getWidth()+10,rightcolorSet.getY(),100,30);
        baseSetPanel.add(mistakecolorSet);

        JButton wenbenBackgroundSet = new JButton("文本框背景颜色");
        wenbenBackgroundSet.setFont(SwingSingleton.tipFont());
        wenbenBackgroundSet.setBounds(mistakecolorSet.getX()+mistakecolorSet.getWidth()+10,rightcolorSet.getY(),140,30);
        baseSetPanel.add(wenbenBackgroundSet);

        JButton qmccolorset = new JButton("全码词颜色");
        qmccolorset.setFont(SwingSingleton.tipFont());
        qmccolorset.setBounds(wenbenBackgroundSet.getX()+wenbenBackgroundSet.getWidth()+10,rightcolorSet.getY(),140,30);
        baseSetPanel.add(qmccolorset);

        JButton backgroundSet = new JButton("整体界面颜色");
        backgroundSet.setFont(SwingSingleton.tipFont());
        backgroundSet.setBounds(rightcolorSet.getX(),rightcolorSet.getY()+rightcolorSet.getHeight()+10,140,30);
        baseSetPanel.add(backgroundSet);

        JButton daziBackgroundSet = new JButton("打字框背景颜色");
        daziBackgroundSet.setFont(SwingSingleton.tipFont());
        daziBackgroundSet.setBounds(backgroundSet.getX()+backgroundSet.getWidth()+10,rightcolorSet.getHeight()+rightcolorSet.getY()+10,140,30);
        baseSetPanel.add(daziBackgroundSet);

        JButton emccolorset = new JButton("二码词颜色");
        emccolorset.setFont(SwingSingleton.tipFont());
        emccolorset.setBounds(daziBackgroundSet.getX()+daziBackgroundSet.getWidth()+10,rightcolorSet.getHeight()+rightcolorSet.getY()+10,100,30);
        baseSetPanel.add(emccolorset);

        JButton smccolorset = new JButton("三码词颜色");
        smccolorset.setFont(SwingSingleton.tipFont());
        smccolorset.setBounds(emccolorset.getX()+emccolorset.getWidth()+10,rightcolorSet.getHeight()+rightcolorSet.getY()+10,100,30);
        baseSetPanel.add(smccolorset);

        fontSizeText = new JTextField();
        fontSizeText.setBounds(backgroundSet.getX(),backgroundSet.getY()+backgroundSet.getHeight()+10,70,30);
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
                        else {
                            e.consume();
                        }
                    }
                }
        );
        fontSizeText.setText(String.valueOf(LocalConfig.fontSize));
        baseSetPanel.add(fontSizeText);

        JButton changeFontSize = new JButton("保存字体大小");
        changeFontSize.setFont(SwingSingleton.tipFont());
        changeFontSize.setBounds(fontSizeText.getX()+ fontSizeText.getWidth()+10,backgroundSet.getY()+backgroundSet.getHeight()+10,90,30);
        baseSetPanel.add(changeFontSize);

        typePageCountText = new JTextField();
        typePageCountText.setBounds(changeFontSize.getX()+changeFontSize.getWidth()+10,backgroundSet.getY()+backgroundSet.getHeight()+10,70,30);
        typePageCountText.addKeyListener(		//只能输入数字
                new KeyListener(){
                    @Override
                    public void keyPressed(KeyEvent e) {}
                    @Override
                    public void keyReleased(KeyEvent e) {
                        // TODO Auto-generated method stub
                        int keyChar = e.getKeyChar();
                        if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){}
                        else {
                            e.consume();
                        }
                    }
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }
                }
        );
        typePageCountText.setText(String.valueOf(LocalConfig.typePageCount));
        baseSetPanel.add(typePageCountText);

        JButton spliteButton = new JButton("保存页字数");
        spliteButton.setFont(SwingSingleton.tipFont());
        spliteButton.setBounds(typePageCountText.getX()+ typePageCountText.getWidth()+10,backgroundSet.getY()+backgroundSet.getHeight()+10,90,30);
        baseSetPanel.add(spliteButton);

        JButton codeTableButton = new JButton("全码表选择");
        codeTableButton.setFont(SwingSingleton.tipFont());
        codeTableButton.setBounds(spliteButton.getX()+spliteButton.getWidth()+10,backgroundSet.getY()+backgroundSet.getHeight()+10,100,30);
        baseSetPanel.add(codeTableButton);

        family = new JComboBox<>();
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String [] fontName=ge.getAvailableFontFamilyNames();
        for (String s : fontName) {
            family.addItem(s);
        }
        family.setBounds(rightcolorSet.getX(), fontSizeText.getY()+ fontSizeText.getHeight()+10,120,30);
        family.setSelectedItem(LocalConfig.family);
        baseSetPanel.add(family);

        JButton familyChange = new JButton("修改字型");
        familyChange.setFont(SwingSingleton.tipFont());
        familyChange.setBounds(family.getX()+family.getWidth()+10, fontSizeText.getY()+ fontSizeText.getHeight()+10,90,30);
        baseSetPanel.add(familyChange);

        readyFont = new JTextField();
        readyFont.setText(String.valueOf(LocalConfig.readyFont));
        readyFont.setBounds(familyChange.getX()+familyChange.getWidth()+10, fontSizeText.getY()+ fontSizeText.getHeight()+10,90,30);
        readyFont.addKeyListener(		//只能输入数字
                new KeyListener(){
                    @Override
                    public void keyPressed(KeyEvent e) {}
                    @Override
                    public void keyReleased(KeyEvent e) {
                        // TODO Auto-generated method stub
                        int keyChar = e.getKeyChar();
                        if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){
                            LocalConfig.readyFont = Integer.valueOf(readyFont.getText());
                        }
                        else {
                            e.consume();
                        }
                    }
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }
                }
        );

        JTextField regex = new JTextField(LocalConfig.regex);
        JButton changeRegex = new JButton("修改选重键");
        addOnBounds(baseSetPanel,regex,family.getX(),columnAddSpacing(family,10),100,30);
        addOnBounds(baseSetPanel,changeRegex,rowAddSpacing(regex,10),regex.getY(),90,30);

        changeRegex.addActionListener(e-> BetterTypingSingleton.setBetterTypingRegex(LocalConfig.regex = regex.getText()));

        baseSetPanel.add(readyFont);

        tabbedPane.add("基本设置", baseSetPanel);

        SetBackgroundListener setbackgroundListener = new SetBackgroundListener();

        wenbenBackgroundSet.addActionListener(setbackgroundListener);
        rightcolorSet.addActionListener(setbackgroundListener);
        mistakecolorSet.addActionListener(setbackgroundListener);
        daziBackgroundSet.addActionListener(setbackgroundListener);
        backgroundSet.addActionListener(setbackgroundListener);
        qmccolorset.addActionListener(setbackgroundListener);
        emccolorset.addActionListener(setbackgroundListener);
        smccolorset.addActionListener(setbackgroundListener);

        spliteButton.addActionListener(e-> LocalConfig.typePageCount = Integer.parseInt(typePageCountText.getText()));
        changeFontSize.addActionListener(e->{
            LocalConfig.fontSize = Integer.parseInt(fontSizeText.getText());
            JTextPaneFontConfig.start();
            TypingListener.getInstance().changeFontColor();
            SwingSingleton.typingText().setFont(new Font(LocalConfig.family, Font.PLAIN, LocalConfig.fontSize));
        });
        codeTableButton.addActionListener(new ChooseFile());
        familyChange.addActionListener(e-> {
            LocalConfig.family = family.getSelectedItem().toString();
            JTextPaneFontConfig.start();
            TypingListener.getInstance().changeFontColor();
        });
    }
}
