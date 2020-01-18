package com.jdragon.tljrobot.client.window;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.event.FArea.*;
import com.jdragon.tljrobot.client.listener.common.Typing;
import com.jdragon.tljrobot.client.listener.core.SystemListener;
import lombok.Data;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;
import static com.jdragon.tljrobot.client.utils.core.Layout.*;

@Data
public class MainFra extends JFrame {
    private static MainFra mainFra;
    public static MainFra getInstance(){
        if(mainFra==null){
            mainFra = new MainFra();
        }
        return mainFra;
    }
    private Point pressedPoint;
    public MainFra(){
        this.setTitle("tlj");
        this.setBounds(LocalConfig.windowX,LocalConfig.windowY,
                LocalConfig.windowWidth,LocalConfig.windowHeight);
        //美化UI
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle
                    .generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        init();
    }
    public void init(){
        JMenu().add(JMenuComponent.getInstance().getMenu());
        addOnBounds(this,JMenu(),5, 10, 45, 32);
        addOnBounds(this,SpeedButton(),
                rowAddSpacing(JMenu(),7),10,150,30);
        addOnBounds(this,KeySpeedButton(),
                rowAddSpacing(SpeedButton(),10),10,150,30);
        addOnBounds(this,KeyLengthButton(),
                rowAddSpacing(KeySpeedButton(),10),10,150,30);
        addOnBounds(this,TheoreticalCodeLengthButton(),
                rowAddSpacing(KeyLengthButton(),10),10,150,30);

        addOnBounds(this,TypingAndWatching(),10,
                columnAddSpacing(SpeedButton(),10),getWidth()-10, getHeight()-100);
        TypingAndWatching().setDividerLocation(400);

        addOnBounds(this,TypingProgress(),10,
                columnAddSpacing(TypingAndWatching(),0),getWidth()-10,10);

        addOnBounds(this,CloseButton(),this.getWidth()-20,0,20,10);
        addOnBounds(this,MaxButton(),this.getWidth()-42,0,20,10);
        addOnBounds(this,MinButton(),this.getWidth()-63,0,20,10);
        addOnBounds(this,SizeButton(),this.getWidth()-10,this.getHeight()-10,10,10);

        addOnBounds(this,QQNameLabel(),TypingAndWatching().getX(),columnAddSpacing(TypingAndWatching(),10),120,40);
        addOnBounds(this,NumberLabel(),rowAddSpacing(QQNameLabel(),10),columnAddSpacing(TypingAndWatching(),10),150,40);
        addOnBounds(this,NumberRecordLabel(),rowAddSpacing(NumberLabel(),10),columnAddSpacing(TypingAndWatching(),10),250,40);
        addOnBounds(this,TipsLabel(),rowAddSpacing(NumberRecordLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
        addOnBounds(this,SendArticleLabel(),rowAddSpacing(TipsLabel(),10),columnAddSpacing(TypingAndWatching(),10),120,40);
    }
    public void addListener(){
        CloseButton().addActionListener(SystemListener.getInstance());
        MaxButton().addActionListener(SystemListener.getInstance());
        MinButton().addActionListener(SystemListener.getInstance());
        SizeButton().addMouseListener(SystemListener.getInstance());
        SizeButton().addMouseMotionListener(SystemListener.getInstance());
        (TypingText().getDocument()).addDocumentListener(Typing.getInstance());
        TypingText().addKeyListener(Typing.getInstance());

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

        manager.addKeyEventPostProcessor(event->{
            if (event.getID() != KeyEvent.KEY_RELEASED) return false;
            switch (event.getKeyCode()) {
                case KeyEvent.VK_F1:
                    SendAchievement.start();
                    break;
                case KeyEvent.VK_F2:
//                    ShareArticle.start();
                    break;
                case KeyEvent.VK_F3:
                    Replay.start();
                    break;
                case KeyEvent.VK_F4:
                    QQGetArticle.start();
                    break;
                case KeyEvent.VK_F5:
                    ChangeQQGroup.start();
                    break;
                case KeyEvent.VK_F6 :
                    ShareArticle.start();
                    break;
                case KeyEvent.VK_F7 :
                    TypingPause.start();
                    break;
//                    case KeyEvent.VK_F8: break;
//                    case KeyEvent.VK_F9 : break;
//                    case KeyEvent.VK_F10 : break;
//                    case KeyEvent.VK_F11 : break;
//                    case KeyEvent.VK_F12 : break;
                default:break;
            }
            return true;
        });
    }
}
