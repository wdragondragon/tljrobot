package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.TypingState.*;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.handle.document.DocumentStyleHandler;
import com.jdragon.tljrobot.client.test.superTest.C;
import com.jdragon.tljrobot.client.utils.common.Timer;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;
import com.jdragon.tljrobot.tljutils.CodePointString;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.CodeEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;
import static com.jdragon.tljrobot.client.entry.TypingState.*;

@Data
@Slf4j
public class TypingListener implements DocumentListener, KeyListener {
    private static TypingListener typingListener;

    private final DocumentStyleHandler documentStyleHandler = DocumentStyleHandler.INSTANCE;

    public TypingListener() {
    }

    public static TypingListener getInstance() {
        if (typingListener == null) {
            typingListener = new TypingListener();
        }
        return typingListener;
    }

    public static boolean delaySendResultSign;//跟打标志，作延迟用
    String leftStr = "qazwsxedcrfvtgb", rightStr = ";/.,。，；、plokmijnuhy";
    CodePointString typeStr = new CodePointString("");
    CodePointString articleStr = new CodePointString("");
//    char[] typeChars;
//    char[] articleChars;

    String[] articleCharCodePoint;
    String[] typeCharCodePoint;
    int oldTypeStrLength;//判断是否为回改而记录的上一次上屏长度
    Timer deleteTextTimer = new Timer();//判断连续回改计时器
    double typingWordsTime;
    Timer typingWordsCompTimer = new Timer();//计算打词的计时器
    boolean isContinuityDeleteText = false;//判断连续回改标志
    int n;
    int typeWordsNumTemp;

    @Override
    public void keyTyped(KeyEvent e) {
        try {
            log.debug("keyTyped：{},code：{}", e.getKeyChar(), e.getKeyCode());
            if (e.getKeyChar() != '\b') {
                typeStr = new CodePointString(typingText().getText() + e.getKeyChar());
            } else {
                typeStr = new CodePointString(typingText().getText());
            }
            articleStr = new CodePointString(Article.getArticleSingleton().getArticle());

//            typeChars = typeStr.toCharArray();
//            articleChars = articleStr.toCharArray();
            articleCharCodePoint = articleStr.toCharArray();
            typeCharCodePoint = typeStr.toCharArray();
            /**
             * 增加已打字数
             */
            if (typeStr.length() > oldTypeStrLength) {
                if (articleCharCodePoint[typeStr.length() - 1].equals(String.valueOf(e.getKeyChar()))) {
                    LocalConfig.localRightNum++;
                    NumState.rightNum++;
                } else {
                    NumState.misNum++;
                    LocalConfig.localMisNum++;
                }
                NumState.num++;
                NumState.dateNum++;
                LocalConfig.localNum++;
                LocalConfig.localDateNum++;
            }
            /**
             * 计算打词率
             */
            try {
                compTypingWords(e.getKeyChar());// 计算打词
            } catch (Exception ignored) {
            }
            oldTypeStrLength = typeStr.length();// 计算当前打字框长度

            if (Constant.TEXT_MODE_EN == LocalConfig.textMode) {
                mistake = 0;
                String[] typeSplit = typeStr.split(" ");
                String[] articleSplit = articleStr.split(" ");
                for (int i = 0; i < typeSplit.length; i++) {
                    if (articleSplit.length - 1 < i || !Objects.equals(typeSplit[i], articleSplit[i])) {
                        mistake++;
                    }
                }
            } else {
                mistake = 0; // 错误字数清零
                for (n = 0; n < typeStr.length(); n++) { // 统计错误字数，向文本框添加字体
                    if (articleCharCodePoint.length - 1 < n || !Objects.equals(typeCharCodePoint[n], articleCharCodePoint[n])) {
                        mistake++;
                    }
                }
            }

            /**
             * 改变字数框
             */
            updateNumShow();

            if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN) || Article.getArticleSingleton().getArticle() == null) {
                return;
            }

            if (typingState) {
                changeFontColor();//改变颜色
            }
            if (LocalConfig.progress)// 进度条
            {
                typingProgress().setValue(typingText().getText().length() + 1 - mistake);
            }
            /**
             * 改变编码提示框
             */
            if (!TypingState.dailyCompetition && typingState) {
                changeTipLabel(typeStr.length());
            }
            changePosition();// 文本自动翻页
        } catch (Exception ignored) {
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            log.debug("keyPressed：{},code：{}", e.getKeyChar(), e.getKeyCode());
//            if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
//                return;
//            }
            if (typeStr.length() > 0 && typeStr.length() <= oldTypeStrLength
                    && e.getKeyChar() == '\b') {// 触发按键时如果打字框长度减小并且按键为BackSpace，即为回改
                TypingState.deleteTextNumber++;
                deleteTextTimer.setEndTime(deleteTextTimer.getEndTime());
                deleteTextTimer.timeStart();
                if (deleteTextTimer.getStartTime() - deleteTextTimer.getEndTime() < 100) {
                    deleteNumber++;
                    isContinuityDeleteText = true;
                } else if (isContinuityDeleteText) {
                    deleteNumber += 2;
                    isContinuityDeleteText = false;
                }
            } else if (isContinuityDeleteText) {
                deleteNumber += 2;
                isContinuityDeleteText = false;
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 计算退格，键法，击键记录
     */
    @Override
    public void keyReleased(KeyEvent e) {
        try {
            log.debug("keyReleased：{},code：{}", e.getKeyChar(), e.getKeyCode());
//            if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
//                return;
//            }
            if (typeStr.length() > 0 && typingState) {
                if (e.getKeyChar() == '\b') {
                    deleteNumber++;
                    record.append("←");
                } else if (e.getKeyChar() == ' ') {
                    record.append("_");
                    space++;
                } else if (leftStr.contains(String.valueOf(e.getKeyChar()))) {
                    record.append(e.getKeyChar());
                    left++;
                } else if (rightStr.contains(String.valueOf(e.getKeyChar()))) {
                    record.append(e.getKeyChar());
                    right++;
                    if (e.getKeyChar() == ';') {
                        repeat++;
                    }
                }
                keyNumber++;
            }
            if (typeStr.length() == 0 && e.getKeyChar() == '\b') {
                deleteNumber++;
                record.append("←");
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
//            if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN) || Article.getArticleSingleton().getArticle() == null) {
//                return;
//            }
            typeStr = new CodePointString(typingText().getText());
            articleStr = new CodePointString(Article.getArticleSingleton().getArticle());

            if (Constant.TEXT_MODE_EN == LocalConfig.textMode) {
                String[] s = typeStr.split(" ");
                typeLength = s.length;
            } else {
                typeLength = typeStr.length();
            }
            if (!typingState && typeStr.length() > 0) {
                init();//打字状态初始化
                typingStart();// 计算第一键时间
                typingState = true; //标记已开始跟打
            }
            if (typeStr.length() < 1) {
                return;
            }
            String typingLastIndexWord = String.valueOf(typeStr.charAt(typeStr.length() - 1));
            String articleLastIndexWord = String.valueOf(articleStr.charAt(articleStr.length() - 1)); // 取两文本最后一个字
            if (typeStr.length() == articleStr.length() && typingLastIndexWord.equals(articleLastIndexWord)
                    && !(LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN))) // 两文本长度相等且最后一字相同时执行
            {
                delaySendResultSign = true;
            }
        } catch (Exception exp) {
            log.error("", exp);
        }
    }

    public void compTypingWords(char c) {
        if (!typeStr.equals("") && typeStr.length() >= oldTypeStrLength) {
            typingWordsCompTimer.setEndTime(typingWordsCompTimer.getStartTime());
            typingWordsCompTimer.timeStart();
            if (typingWordsCompTimer.getStartTime() - typingWordsCompTimer.getEndTime() < 50) {
                typeWordsNumTemp++;
                typingWordsTime = timer.getSecond();
            } else if (typeWordsNumTemp != 0) {
                StringBuilder temp = new StringBuilder();
                typeWordsNum += typeWordsNumTemp + 1;
                for (int k = typeStr.length() - typeWordsNumTemp - 2; k <= typeStr.length() - 2; k++) {
                    temp.append(articleCharCodePoint[k]);
                }
                typeWordsNumTemp = 0; // 当前词长度清零
                WordsState wordsState =
                        typeWordsList.get(typeWordsList.size() - 1);
                if (wordsState.getWords().equals(temp.substring(0, 1))) // 单字对比
                {
                    typeWordsList.remove(typeWordsList.get(typeWordsList.size() - 1));
                }
                typeWordsList.add(new WordsState(getSpeed(), getKeySpeed(), compInstantaneousSpeed(), temp.toString(), typingWordsTime));
            } else {
                typeWordsList.add(new WordsState(getSpeed(), getKeySpeed(), compInstantaneousSpeed(), String.valueOf(c), timer.getSecond()));
            }
        }
    }

    /**
     * @Author: Jdragon on 2020.01.20 上午 12:32
     * @param: [index]
     * @return: void
     * @Description 根据跟打进度来改变词语提示框的内容
     */
    public void changeTipLabel(int index) {
        CodeEntity codeEntity = Article.getArticleSingleton()
                .getShortCodeEntity().getCodeEntities()[index];
        StringBuilder tipStr = new StringBuilder();
        String word = codeEntity.getWord();
        String wordCode = codeEntity.getWordCode();
        String words = "", wordsCode = "";
        tipStr.append(word).append(":").append(wordCode);
        if (codeEntity.getWords() != null) {
            words = codeEntity.getWords();
            wordsCode = codeEntity.getWordsCode();
            tipStr.append("  ").append(words).append(":").append(wordsCode);
        }
        tipsLabel().setText(tipStr.toString());// 单字编码提示更改
        int chineseLength = word.length() + words.length();//中文长度
        int englishLength = wordCode.length() + wordsCode.length();//英文长度
        int subWidth = chineseLength * 12 + (englishLength + 4) * 8 - tipsLabel().getWidth();//用中英文长度来计算改变的提示宽度
        Layout.addSize(subWidth, 0, tipsLabel());
        Layout.addLocation(subWidth, 0, sendArticleLabel());
    }

    /**
     * @Author: Jdragon on 2020.01.12 下午 9:53
     * @param: []
     * @return: double
     * @Description 通过计算前5次上屏计算瞬时速度
     */
    public double compInstantaneousSpeed() {
        StringBuilder typeWordsStrTemp;
        int typeWordsNum = typeWordsList.size();
        if (typeWordsNum > 5) {
            typeWordsStrTemp = new StringBuilder();
            WordsState first = typeWordsList.get(typeWordsNum - 6);
            WordsState lastIndex = typeWordsList.get(typeWordsNum - 1);
            for (int j = typeWordsNum - 5; j < typeWordsNum; j++) {
                WordsState typingWordsTemp = typeWordsList.get(j);
                typeWordsStrTemp.append(typingWordsTemp.getWords());
            }
            int length1 = typeWordsStrTemp.length();
            double instantaneousTime = lastIndex.getSpeed() - first.getSpeed();
            double instantaneousSpeed = length1 / instantaneousTime;
            return instantaneousSpeed * 60;
        }
        return 0;
    }

    public void changeAllFontColor() {
        try {
            watchingText().setText(""); // 清空文本框
            try {
                for (n = 0; n < articleStr.length(); n++) { // 统计错误字数，向文本框添加字体
                    if (typeCharCodePoint.length > n && !Objects.equals(typeCharCodePoint[n], articleCharCodePoint[n])) {
                        documentStyleHandler.insertDoc(
                                String.valueOf(articleCharCodePoint[n]), "红");
                    } else {
                        documentStyleHandler.insertDoc(
                                String.valueOf(articleCharCodePoint[n]), "黑");
                    }
                }
            } catch (Exception e) {
                n = 0;
                log.error("", e);
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public static byte[] intToBytes(int a) {
        byte[] ans = new byte[4];
        for (int i = 0; i < 4; i++)
            ans[i] = (byte) (a >> (i * 8));//截断 int 的低 8 位为一个字节 byte，并存储起来
        return ans;
    }

    public static int bytesToInt(byte[] a) {
        int ans = 0;
        for (int i = 0; i < 4; i++) {
            ans <<= 8;
            ans |= (a[3 - i] & 0xff);
            /* 这种写法会看起来更加清楚一些：
            int tmp=a[3-i];
            tmp=tmp&0x000000ff;
            ans|=tmp;*/
            intPrint(ans);
        }
        return ans;
    }

    public static void intPrint(int a) {//将 int 按位从左到右打印
        int count = 0;
        for (int i = 31; i >= 0; i--) {
            System.out.print((a >> i) & 1);
            count++;
            if (count == 4) {//每四位为一组，用空格分开
                System.out.print(" ");
                count = 0;
            }
        }
        System.out.println();
    }

    public static void bytePrint(byte a) {//将 byte 按位从左到右打印
        int count = 0;
        for (int i = 7; i >= 0; i--) {
            System.out.print((a >> i) & 1);
            count++;
            if (count == 4) {//每四位为一组，用空格分开
                System.out.print(" ");
                count = 0;
            }
        }
        System.out.println();
    }

    public static String unicode2String(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (String s : hex) {
            int data = Integer.parseInt(s, 16);
            string.append((char) data);
        }
        return string.toString();
    }

    public static String string2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append("\\u").append(Integer.toHexString(c));
        }
        return unicode.toString();
    }


    public String[] toArticleChars(String articleStr) {
        List<String> articleCodePoint = new LinkedList<>();
        for (int i = 0; i < articleStr.length(); i++) {
            if (i + 1 == articleStr.length() || articleStr.codePointCount(i, i + 2) > 1) {
                articleCodePoint.add(articleStr.substring(i, i + 1));
            } else {
                articleCodePoint.add(articleStr.substring(i, i + 2));
                i = i + 1;
            }
        }
        return articleCodePoint.toArray(new String[0]);
    }

    /**
     * @Author: Jdragon on 2020.01.20 上午 12:33
     * @param: []
     * @return: void
     * @Description 分页显示，词语提示等功能实现
     */
    private String thisPageTypeStr;
    int thisPageNum = -1;

    public void changeFontColor() {
        int pageCount = LocalConfig.typePageCount;
        articleStr = Article.getArticleSingleton().getArticle() != null ? new CodePointString(Article.getArticleSingleton().getArticle()) : new CodePointString("");
//        articleChars = articleStr.toCharArray();
        articleCharCodePoint = articleStr.toCharArray();
        int prePageNum = thisPageNum;
        thisPageNum = typeStr.length() / pageCount;
        int lastIndex;
        int pageMore = (widthFontNum + 1) / 3;
        int moreSign = thisPageNum >= 1 ? 1 : 0;
        if (articleStr.length() - pageCount * thisPageNum > pageCount + pageMore) {
            lastIndex = (thisPageNum + 1) * pageCount + pageMore;
        } else {
            lastIndex = articleStr.length();
        }
        thisPageTypeStr = typeStr.substring(pageCount * thisPageNum);

        watchingText().setText(""); // 清空文本框
        try {
            if (thisPageNum > 0) {
                n = thisPageNum * pageCount - pageMore;
            } else {
                n = thisPageNum * pageCount;
            }
//            if (thisPageNum != prePageNum || watchingText().getText().length() != lastIndex - n) {
//                watchingText().setText(""); // 清空文本框
//                watchingText().setCaretPosition(0);
//            }
            for (; n < (Math.min(typeStr.length(), articleStr.length())); n++) { // 统计错误字数，向文本框添加字体
                if (!Objects.equals(typeCharCodePoint[n], articleCharCodePoint[n]) && typingState) {
//                    documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, "红");
                    documentStyleHandler.insertDoc(articleCharCodePoint[n], "红");
                } else if (typingState) {
//                    documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, "黑");
                    documentStyleHandler.insertDoc(articleCharCodePoint[n], "黑");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            n = 0;
        }
        if (!typingState) {
            n = 0;
        }
        DocumentStyleHandler.INSTANCE.defineStyle("预读",
                LocalConfig.fontSize, false, false, false,
                LocalConfig.watchingBackgroundColor, LocalConfig.family, null);
        int readyFontNum = Integer.parseInt(SetDialog.readyFont.getText());
        if (n >= thisPageNum * (pageCount - 1) + readyFontNum) {
            int tempReady = n + readyFontNum;
            if (tempReady > lastIndex) {
                tempReady = lastIndex;
            }
            for (; n < tempReady; n++) {
//                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, "预读");
                documentStyleHandler.insertDoc(articleCharCodePoint[n], "预读");
            }
        }
        MutableAttributeSet attrs = new SimpleAttributeSet();
        attrs.addAttribute("UnderlineOpen", true);
        attrs.addAttribute("Underline-Color", Color.red);
        CodeEntity[] codeEntities =
                Article.getArticleSingleton().getShortCodeEntity().getCodeEntities();
        for (; n < lastIndex; n++) { // 添加剩下字体
            if (n >= Article.getArticleSingleton().getShortCodeEntity().getArticle().length()) {
                documentStyleHandler.insertDoc(articleCharCodePoint[n], "黑");
                break;
            }
            if (!LocalConfig.tip || TypingState.dailyCompetition
                    || LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)) {
//                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, "灰");
                documentStyleHandler.insertDoc(articleCharCodePoint[n], "灰");
            } else {
                int type = codeEntities[n].getType();
                boolean isBold = codeEntities[n].isBold();
                int next = codeEntities[n].getNext();
                int strLength = articleCharCodePoint[n].length();
                if (!isBold) {
                    switch (type) {
                        case 0:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "灰");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "灰");
                            break;
                        case 1:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿");
                            break;
                        case 2:
                            String codes = codeEntities[n].getWordsCode();
                            if (codes != null) {
                                String number = codes.substring(codes.length() - 1);
                                attrs.addAttribute("Number", number);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿斜");
                            }

                            break;
                        case 3:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝");
                            break;
                        case 4:
                            String codes1 = codeEntities[n].getWordsCode();
                            if (codes1 != null) {
                                String number1 = codes1.substring(codes1.length() - 1);
                                attrs.addAttribute("Number", number1);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝斜");
                            }
                            break;
                        case 5:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉");
                            break;
                        case 6:
                            String codes2 = codeEntities[n].getWordsCode();
                            if (codes2 != null) {
                                String number2 = codes2.substring(codes2.length() - 1);
                                attrs.addAttribute("Number", number2);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉斜");
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (type) {
                        case 0:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "灰");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "灰");
                            break;
                        case 1:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿粗");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿粗");
                            break;
                        case 2:
                            String codes = codeEntities[n].getWordsCode();
                            if (codes != null) {
                                String number = codes.substring(codes.length() - 1);
                                attrs.addAttribute("Number", number);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿粗", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿粗", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "绿粗斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "绿粗斜");
                            }
                            break;
                        case 3:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝粗");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝粗");
                            break;
                        case 4:
                            String codes1 = codeEntities[n].getWordsCode();
                            if (codes1 != null) {
                                String number1 = codes1.substring(codes1.length() - 1);
                                attrs.addAttribute("Number", number1);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝粗", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝粗", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "蓝粗斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "蓝粗斜");
                            }
                            break;
                        case 5:
//                            documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉粗");
                            documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉粗");
                            break;
                        case 6:
                            String codes2 = codeEntities[n].getWordsCode();
                            if (codes2 != null) {
                                String number2 = codes2.substring(codes2.length() - 1);
                                attrs.addAttribute("Number", number2);
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉粗", attrs);
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉粗", attrs);
                            } else {
//                                documentStyleHandler.updateDocStyle(n - pageCount * thisPageNum + moreSign * pageMore, next - n + strLength, "粉粗斜");
                                documentStyleHandler.insertDoc(articleStr.substring(n, next + 1), "粉粗斜");
                            }
                            break;
                        default:
                            break;
                    }
                }
                n = next;
            }
        }
        System.out.println("color change");
    }

    public void changeLookPlayFontColor(List<HashMap<String, Integer>> strList) {
        watchingText().setText(""); // 清空文本框
        for (HashMap<String, Integer> hashMap : strList) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                if (entry.getValue() == 0) {
                    documentStyleHandler.insertDoc(entry.getKey(), "对");
                } else if (entry.getValue() == 1) {
                    lookMiss++;
                    documentStyleHandler.insertDoc(entry.getKey(), "少");
                } else if (entry.getValue() == 2) {
                    lookMore++;
                    documentStyleHandler.insertDoc(entry.getKey(), "多");
                } else if (entry.getValue() == 3) {
                    lookMis++;
                    documentStyleHandler.insertDoc(entry.getKey(), "错");
                } else {
                    documentStyleHandler.insertDoc(entry.getKey(), "错原");
                }
            }
        }
        mistake = lookMis + lookMore + lookMiss;
    }

    public void changeListenPlayFontColor(List<HashMap<String, Integer>> strList) {
        int length = 0;
        watchingText().setText(""); // 清空文本框
        for (HashMap<String, Integer> hashMap : strList) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                length++;
                if (entry.getValue() == 0) {
                    documentStyleHandler.insertDoc(entry.getKey(), "对");
                } else if (entry.getValue() == 1) {
                    lookMiss++;
                    documentStyleHandler.insertDoc(entry.getKey(), "少");
                } else if (entry.getValue() == 2) {
                    lookMore++;
                    documentStyleHandler.insertDoc(entry.getKey(), "多");
                } else if (entry.getValue() == 3) {
                    lookMis++;
                    documentStyleHandler.insertDoc(entry.getKey(), "错");
                } else if (entry.getValue() == 4) {
                    documentStyleHandler.insertDoc(entry.getKey(), "错原");
                } else {
                    documentStyleHandler.insertDoc(entry.getKey(), "忽略");
                    length--;
                }
            }
        }
        mistake = lookMis + lookMore + lookMiss;
        ListenPlayEvent.setLength(length);
    }

    /**
     * @Author: Jdragon on 2020.01.20 上午 12:33
     * @param: []
     * @return: void
     * @Description 根据打字进度来进行翻页
     */
    int widthFontNum, heightFontNum;// 一行字数,行数
    int cursor = 116;//光标所在位置
    int maxPageNum;
    int pageCount, fontSize;

    public void changePosition() {// 自动滚动条翻页方法
        fontSize = LocalConfig.fontSize;
        pageCount = LocalConfig.typePageCount;
        int fontWidth = fontSize + 59; // 一个字横分辨率
        int fontHeight = fontSize + 14 + fontSize / 2;// 一个字竖分辨率
        heightFontNum = typingAndWatching().getDividerLocation() / fontHeight; // 行数
        widthFontNum = (typingAndWatching().getWidth() - fontWidth) / fontSize;//行字数
        cursor = cursor % (LocalConfig.typePageCount + (widthFontNum + 1) / 3);
        maxPageNum = (heightFontNum - 1) * (widthFontNum + 1);
        JScrollBar jsb = watchingJsp().getVerticalScrollBar();
        int pageTotal = Math.min(pageCount, watchingText().getText().length());
        if (pageTotal > maxPageNum) {
            int jsbValue = (int) (((thisPageTypeStr.length() - maxPageNum / 2) / (double) (pageTotal - maxPageNum)) * (jsb
                    .getMaximum() - jsb.getHeight()));
            jsbValue = Math.max(jsbValue, 0);
            jsb.setValue(jsbValue);
        }
    }

    public void updateNumShow() {
        numberLabel().setText("字数:" + articleStr.length() + "/已打:" + typeStr.length() + "/错:"
                + mistake);
        if (UserState.loginState) {
            numberRecordLabel().setText("总:" + NumState.num + " 对:"
                    + NumState.rightNum + " 错:"
                    + NumState.misNum + " 今:"
                    + NumState.dateNum);
        } else {
            numberRecordLabel().setText("总:" + LocalConfig.localNum + " 对:"
                    + LocalConfig.localRightNum + " 错:"
                    + LocalConfig.localMisNum + " 今:"
                    + LocalConfig.localDateNum);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }
}
