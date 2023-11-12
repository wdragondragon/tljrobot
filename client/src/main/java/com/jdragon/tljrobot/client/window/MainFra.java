package com.jdragon.tljrobot.client.window;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.FArea.*;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.event.other.SwitchFollowPlayEvent;
import com.jdragon.tljrobot.client.event.other.SwitchListenPlayEvent;
import com.jdragon.tljrobot.client.event.other.SwitchWatchPlayEvent;
import com.jdragon.tljrobot.client.handle.theme.ThemeManager;
import com.jdragon.tljrobot.client.handle.theme.WindowThemeHandler;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import com.jdragon.tljrobot.client.listener.core.SystemListener;
import com.jdragon.tljrobot.client.utils.common.BetterTypingSingleton;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.DrawUnLookPlayResult;
import com.jdragon.tljrobot.client.window.dialog.LogonDialog;
import com.jdragon.tljrobot.client.window.dialog.SendArticleDialog;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;
import com.jdragon.tljrobot.client.window.dialog.ShowArticleDialog;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import com.jdragon.tljrobot.tljutils.string.Comparison;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;
import static com.jdragon.tljrobot.client.utils.core.Layout.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class MainFra extends JFrame {
    private static MainFra mainFra;

    public static MainFra getInstance() {
        if (mainFra == null) {
            mainFra = new MainFra();
        }
        return mainFra;
    }

    private Point pressedPoint;

    public MainFra() {
        this.setTitle("长流跟打器" + FinalConfig.VERSION);
        this.setBounds(LocalConfig.windowX, LocalConfig.windowY,
                LocalConfig.windowWidth, LocalConfig.windowHeight);
        //美化UI
        WindowThemeHandler theme = ThemeManager.getTheme(LocalConfig.windowsTheme);
        theme.activation();
        init();
    }

    public void init() {
        jMenu().add(JMenuComponent.getInstance().getMenu());
        addOnBounds(this, tipButton(), 50, 0, 20, 10);
        addOnBounds(this, jMenu(), 5, 10, 45, 32);
        int topButtonWidth = (LocalConfig.windowWidth - (jMenu().getX() + jMenu().getWidth() + 40)) / 4;
        addOnBounds(this, speedButton(),
                xSpace(jMenu(), 7), 10, topButtonWidth, 30);
        addOnBounds(this, keySpeedButton(),
                xSpace(speedButton(), 10), 10, topButtonWidth, 30);
        addOnBounds(this, keyLengthButton(),
                xSpace(keySpeedButton(), 10), 10, topButtonWidth, 30);
        addOnBounds(this, theoreticalCodeLengthButton(),
                xSpace(keyLengthButton(), 10), 10, topButtonWidth, 30);

        addOnBounds(this, typingAndWatching(), 0,
                ySpace(speedButton(), 5), getWidth(), getHeight() - 95);

        typingAndWatching().setDividerLocation(400);

        addOnBounds(this, typingProgress(), 0,
                ySpace(typingAndWatching(), 0), getWidth(), 10);

        addOnBounds(this, closeButton(), this.getWidth() - 20, 0, 20, 10);
        addOnBounds(this, maxButton(), this.getWidth() - 42, 0, 20, 10);
        addOnBounds(this, minButton(), this.getWidth() - 63, 0, 20, 10);
        addOnBounds(this, sizeButton(), this.getWidth() - 10, this.getHeight() - 10, 10, 10);

        closeButton().setVisible(LocalConfig.undecorated);
        maxButton().setVisible(LocalConfig.undecorated);
        minButton().setVisible(LocalConfig.undecorated);
        sizeButton().setVisible(LocalConfig.undecorated);

        int baseBottomLabelWidth = (LocalConfig.windowWidth - 15) / 8;
        addOnBounds(this, qQNameLabel(), typingAndWatching().getX(), ySpace(typingAndWatching(), 10), baseBottomLabelWidth * 3 / 2, 40);
        addOnBounds(this, numberLabel(), xSpace(qQNameLabel(), 5), qQNameLabel().getY(), baseBottomLabelWidth * 3 / 2, 40);
        addOnBounds(this, numberRecordLabel(), xSpace(numberLabel(), 5), qQNameLabel().getY(), 3 * baseBottomLabelWidth, 40);
        addOnBounds(this, tipsLabel(), xSpace(numberRecordLabel(), 5), qQNameLabel().getY(), 2 * baseBottomLabelWidth, 40);
        addOnBounds(this, sendArticleLabel(), xSpace(tipsLabel(), 5), qQNameLabel().getY(), 0, 40);
    }

    int preButton;

    public void addListener() {
        tipButton().addActionListener(e -> {
            tipButton().setSelected(!tipButton().isSelected());
            SetDialog.getTipsButton().setSelected(!SetDialog.getTipsButton().isSelected());
        });
        closeButton().addActionListener(SystemListener.getInstance());
        maxButton().addActionListener(SystemListener.getInstance());
        minButton().addActionListener(SystemListener.getInstance());
        sizeButton().addMouseListener(SystemListener.getInstance());
        sizeButton().addMouseMotionListener(SystemListener.getInstance());
        (typingText().getDocument()).addDocumentListener(TypingListener.getInstance());
        typingText().addKeyListener(TypingListener.getInstance());
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


        manager.addKeyEventPostProcessor(event -> {
            if (event.getID() != KeyEvent.KEY_PRESSED) {
                return false;
            }
            if (preButton == 17) {
                switch (event.getKeyCode()) {
                    case 'Z':
                        SetDialog.getInstance().setVisible(true);
                        break;
                    case 'P':
                        if (TypingState.sendArticle == Constant.SEND_EXTRACT) {
                            ArticleTreeListener.getInstance().chouqu("下一段");
                        } else if (TypingState.sendArticle == Constant.SEND_ORDER) {
                            ArticleTreeListener.getInstance().nextOrder();
                        } else if (TypingState.sendArticle == Constant.SEND_WORDS) {
                            ArticleTreeListener.getInstance().ciKuNext();
                        }
                        break;
                    case 'S':
                        ArticleTreeListener.getInstance().save();
                        break;
                    case 'L':
                        MixListener.getInstance().mixButton("该段乱序");
                        break;
                    case 'E':
                        if (TypingState.sendArticle != 0) {
                            JOptionPane.showMessageDialog(mainFra, "请先结束发文");
                            break;
                        }
                        String all = Clipboard.get();
                        if (Constant.TEXT_MODE_EN == LocalConfig.textMode) {
                            all = ArticleUtil.leaveOneSpace(all);
                            String text = SendArticleDialog.enCikuDelimiter.getText();
                            if (!text.equals(" ")) {
                                text += " ";
                            }
                            char[] charArray = text.toCharArray();
                            for (int i = charArray.length; i > 0; i--) {
                                char[] chars = Arrays.copyOf(charArray, i);
                                String delimiter = String.valueOf(chars);
                                if (all.endsWith(delimiter)) {
                                    all = all.substring(0, all.length() - delimiter.length());
                                    break;
                                }
                            }
                        }
                        Article.getArticleSingleton(1, "剪贴板载文", all);
                        ReplayEvent.start();
                        break;
                    case 'Q':
                        SwitchListenPlayEvent.start();
                        break;
                    case 'K':
                        SwitchWatchPlayEvent.start();
                        break;
                    case 'G':
                        SwitchFollowPlayEvent.start();
                        break;
                    case 'W':
                        Article.getArticleSingleton(1, "随机一文", ArticleUtil.getRandomContent2());
                        ReplayEvent.start();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)) {
                            if (SwingSingleton.typingText().getText().length() == 0) {
                                break;
                            }
                            typingText().setEditable(false); // 设置不可打字状态
                            TypingListener.delaySendResultSign = true;
                        } else if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
                            typingText().setEditable(false); // 设置不可打字状态
                            TypingListener.delaySendResultSign = true;
                        }
                        break;
                    default:
                        break;
                }
                typingText().requestFocusInWindow();
            }
            preButton = event.getKeyCode();
            switch (event.getKeyCode()) {
                case KeyEvent.VK_F1:
                    SendAchievementEvent.start();
                    break;
                case KeyEvent.VK_F2:
                    SendArticleEvent.start();
                    break;
                case KeyEvent.VK_F3:
                    ReplayEvent.start();
                    break;
                case KeyEvent.VK_F4:
                    if (TypingState.sendArticle != 0) {
                        JOptionPane.showMessageDialog(mainFra, "请先结束发文");
                        break;
                    }
                    if (!LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
                        QQGetArticleEvent.start();
                    } else {
                        ListenPlayEvent.start();
                    }
                    break;
                case KeyEvent.VK_F5:
                    ChangeQQGroupEvent.start();
                    break;
                case KeyEvent.VK_F6:
                    ShareArticleEvent.start();
                    break;
                case KeyEvent.VK_F7:
                    TypingPauseEvent.start();
                    break;
//                    case KeyEvent.VK_F8: new web(); break;
//                    case KeyEvent.VK_F9 : break;
//                    case KeyEvent.VK_F9 :
//                        TypingText().setEditable(false); // 设置不可打字状态
//                        Typing.delaySendResultSign = true;
//                        break;
//                    case KeyEvent.VK_F11 : break;
//                    case KeyEvent.VK_F12 : break;
                case KeyEvent.VK_ESCAPE:
                    LogonDialog.getInstance().setVisible(false);
                    SendArticleDialog.getInstance().setVisible(false);
                    SetDialog.getInstance().setVisible(false);
                    ShowArticleDialog.getInstance("").setVisible(false);
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}
