package com.jdragon.tljrobot.client.listener.common;


import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypeNum;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.TypingState.*;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;
import com.jdragon.tljrobot.client.utils.common.Timer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.jdragon.tljrobot.client.entry.TypingState.*;
import static com.jdragon.tljrobot.client.factory.SwingSingleton.*;

public class Typing implements DocumentListener, KeyListener {
    private static Typing typingListener;
    public Typing(){}
    public static Typing getInstance(){
        if(typingListener==null) typingListener = new Typing();
        return typingListener;
    }

    String leftStr = "qazwsxedcrfvtgb", rightStr = ";/.,。，；、plokmijnuhy";
    String typeStr = new String();
    String articleStr = new String();
    char []typeChars;
    char []articleChars;
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
            if (e.getKeyChar() != '\b')
                typeStr = TypingText().getText() + e.getKeyChar();
            else
                typeStr = TypingText().getText();
            articleStr = Article.getArticleSingleton().getArticle();
            typeChars = typeStr.toCharArray();
            articleChars = articleStr.toCharArray();
            if (typeStr.length() > oldTypeStrLength) {
                if (articleChars[typeStr.length() - 1] == e.getKeyChar())
                    TypeNum.rightNum++;
                else
                    TypeNum.misNum++;
                TypeNum.allNum++;
                TypeNum.dateNum++;
            }
            // 计算打词率
            try {
                compTypingWords(e.getKeyChar());// 计算打词
            } catch (Exception ex) {
                System.out.println("打词计算失败180genda");
            }
            mistake = 0; // 错误字数清零
            oldTypeStrLength = typeStr.length();// 计算当前打字框长度
            for (n = 0; n < typeStr.length(); n++) { // 统计错误字数，向文本框添加字体
                if (typeChars[n] != articleChars[n]) {
                    mistake++;
                    String mistakeStr = "\"" + articleChars[n] + "\"在第"
                            + (n + 1) + "个字\n";
                    mistakeList.add(mistakeStr);
//                    missign.add(n);
                }
            }
            if (typingState)
                changeFontColor();
//                ChangeFontColor(); // 改变字体颜色

//            tipschange.changeTips(String.valueOf(articleChars[typeStr.length()]));// 单字编码提示更改

            NumberLabel().setText("字数:" + articleStr.length() + "/已打:" + typeStr.length() + "/错"
                    + mistake);
            NumberRecordLabel().setText("总:" + TypeNum.allNum + " 对:"
                    + TypeNum.rightNum + " 错:"
                    + TypeNum.misNum + " 今:"
                    + TypeNum.dateNum);
//            readWrite.keepfontnum(Window.fontallnum, Window.rightnum,
//                    Window.misnum);
//            try {
//                RecordChange.recordChange();
//            } catch (Exception ex) {
//                System.out.println("发送跟打字数失败196genda");
//            }
//            if (SetFrameJinduListener.jindusign == 1)// 判断是否开了进度条
//                gendajindu.jindu(dazi.getText().length() + 1 - (int) (mistake));
            changePosition();// 文本自动翻页

        } catch (Exception exp) {exp.printStackTrace();}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            if (typeStr.length() > 0 && typeStr.length() <= oldTypeStrLength
                    && e.getKeyChar() == '\b') {// 触发按键时如果打字框长度减小并且按键为BackSpace，即为回改
                TypingState.deleteTextNumber++;
                // System.out.println("回改+");
                deleteTextTimer.setEndTime(deleteTextTimer.getEndTime());
                deleteTextTimer.timeStart();
                if (deleteTextTimer.getStartTime() - deleteTextTimer.getEndTime() < 100) {
                    deleteNumber++;
                    // System.out.println("退格+");
                    isContinuityDeleteText = true;
                } else if (isContinuityDeleteText) {
                    // System.out.println("退格+2");
                    deleteNumber += 2;
                    isContinuityDeleteText = false;
                }
            } else if (isContinuityDeleteText) {
                System.out.println("退格+2");
                deleteNumber += 2;
                isContinuityDeleteText = false;
            }
        } catch (Exception ex) {
            System.out.println("跟打框无字2");
        }
    }
    /**
     * 计算退格，键法，击键记录
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        try {
            if (typeStr.length() > 0 && typingState) {
                if (e.getKeyChar() == '\b') {
                    deleteNumber++;
                    record.append("←");
                } else if (e.getKeyChar() == ' ') {
                    record.append("_");
                    space++;
                } else if (leftStr.indexOf(String.valueOf(e.getKeyChar())) >= 0) {
                    record.append(String.valueOf(e.getKeyChar()));
                    left++;
                } else if (rightStr.indexOf(String.valueOf(e.getKeyChar())) >= 0) {
                    record.append(String.valueOf(e.getKeyChar()));
                    right++;
                    if (e.getKeyChar() == ';')
                        repeat++;
                }
                keyNumber++;
                SpeedButton().setText(String.format("%.2f", TypingState.getSpeed()));
            }
            if (typeStr.length() == 0 && e.getKeyChar() == '\b') {
                deleteNumber++;
                // System.out.println("退格+");
                record.append("←");
            }
        } catch (Exception ex) {
            System.out.println("跟打框无字1");
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            typeStr = TypingText().getText();
            articleStr = Article.getArticleSingleton().getArticle();
            typeLength = typeStr.length();
            if (!typingState&&typeLength > 0) {
                init();//打字状态初始化
                typingStart();// 计算第一键时间
                typingState = true; //标记已开始跟打
            } else
                typingEnd();//计算最后一键的时间
            
            String typingLastIndexWord = String.valueOf(typeStr.charAt(typeStr.length() - 1));
            String articleLastIndexWord = String.valueOf(articleStr.charAt(articleStr.length() - 1)); // 取两文本最后一个字
            if (typeStr.length() == articleStr.length() && typingLastIndexWord.equals(articleLastIndexWord)
                    && !(LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN))) // 两文本长度相等且最后一字相同时执行
            {
                TypingText().setEditable(false); // 设置不可打字状态
                changeAllFontColor();
                typingState = false;//跟打结束标志
            }
        } catch (Exception exp) {exp.printStackTrace();}
    }
    public void compTypingWords(char c) {
        if (typeStr != "" && typeStr.length() >= oldTypeStrLength) {
            typingWordsCompTimer.setEndTime(typingWordsCompTimer.getStartTime());
            typingWordsCompTimer.timeStart();
            if (typingWordsCompTimer.getStartTime() - typingWordsCompTimer.getEndTime() < 50) {
                typeWordsNumTemp++;
                typingWordsTime = timer.getSecond();
            } else if (typeWordsNumTemp != 0) {
                String temp = "";
                typeWordsNum += typeWordsNumTemp + 1;
                for (int k = typeStr.length() - typeWordsNumTemp - 2; k <= typeStr.length() - 2; k++) {
                    temp += String.valueOf(articleChars[k]);
                }
                typeWordsNumTemp = 0; // 当前词长度清零
                WordsState wordsState =
                        typeWordsList.get(typeWordsList.size() - 1);
                if (wordsState.getWords().equals(temp.substring(0, 1))) // 单字对比
                    typeWordsList.remove(typeWordsList.get(typeWordsList.size() - 1));
                typeWordsList.add(new WordsState(getSpeed(),getKeySpeed(), compInstantaneousSpeed(),temp));
            } else if (typeWordsNumTemp == 0) {
                typeWordsList.add(new WordsState(getSpeed(),getKeySpeed(), compInstantaneousSpeed(),String.valueOf(c)));
            }
        }
    }
    /**
     * @Author: Jdragon on 2020.01.12 下午 9:53
     * @param: []
     * @return: double
     * @Description 通过计算前5次上屏计算瞬时速度
     */
    public double compInstantaneousSpeed() {
        String TypeWordsStrTemp;
        int typeWordsNum = typeWordsList.size();
        if (typeWordsNum > 5) {
            TypeWordsStrTemp = new String();
            WordsState first = typeWordsList.get(typeWordsNum - 6);
            WordsState lastIndex = typeWordsList.get(typeWordsNum - 1);
            for (int j = typeWordsNum - 5; j < typeWordsNum; j++) {
                WordsState typingWordsTemp = typeWordsList.get(j);
                TypeWordsStrTemp += typingWordsTemp.getWords();
            }
            int length1 = TypeWordsStrTemp.length();
            double instantaneousTime = lastIndex.getSpeed() - first.getSpeed();
            double instantaneousSpeed = length1 / instantaneousTime;
            return instantaneousSpeed * 60;
        }
        return 0;
    }
    public void changeAllFontColor() {
        try {
            WatchingText().setText(""); // 清空文本框
            try {
                for (n = 0; n < articleStr.length(); n++) { // 统计错误字数，向文本框添加字体
                    if (typeChars[n] != articleChars[n]&&typeLength<n)
                        JTextPaneFont.insertDoc(JTextPaneFont.styledDoc,
                                String.valueOf(articleChars[n]), "红", WatchingText());
                    else
                        JTextPaneFont.insertDoc(JTextPaneFont.styledDoc,
                                String.valueOf(articleChars[n]), "黑", WatchingText());
                }
            } catch (Exception e) {
                n = 0;
                System.out.println("wussssss");
            }
        } catch (Exception ex) {
            System.out.println("跟打框无字3");
            ex.printStackTrace();
        }
    }
    private String thisPageTypeStr;
    int thisPageNum;
    public void changeFontColor() {
        try {
            int pageCount = LocalConfig.typeCount;
            articleStr = Article.getArticleSingleton().getArticle();
            articleChars = articleStr.toCharArray();
            thisPageNum = typeStr.length() / pageCount;
            int lastIndex;
            if (articleStr.length() - pageCount * thisPageNum > pageCount) {
//                String thisPageArticle = articleStr.substring(pageCount * thisPageNum,
//                        (thisPageNum + 1) * pageCount);
                lastIndex = (thisPageNum + 1) * pageCount + (heng + 1) / 3;
            } else {
//                String thisPageArticle = articleStr.substring(pageCount * thisPageNum);
                lastIndex = articleStr.length();
            }
            thisPageTypeStr = typeStr.substring(pageCount * thisPageNum);
            WatchingText().setText(""); // 清空文本框
            // n = 0;
            try {
                if (thisPageNum > 0)
                    n = thisPageNum * pageCount - (heng + 1) / 3;
                else
                    n = thisPageNum * pageCount;
                for (; n < typeStr.length(); n++) { // 统计错误字数，向文本框添加字体
                    if (typeChars[n] != articleChars[n] && typingState) {
                        JTextPaneFont.insertDoc(JTextPaneFont.styledDoc,
                                String.valueOf(articleChars[n]), "红", WatchingText());
                    } else if (typingState)
                        JTextPaneFont.insertDoc(JTextPaneFont.styledDoc,
                                String.valueOf(articleChars[n]), "黑", WatchingText());
                }
            } catch (Exception e) {
                n = 0;
                System.out.println("wussssss");
            }
            if (!typingState)
                n = 0;

            int n2 = n;
            for (n = n2; n < lastIndex; n++) { // 添加剩下字体
                // System.out.print(n);
                if (n >= Article.getArticleSingleton().getShortCodeEntity().getCodeLength()) break;
//                long startTime = System.nanoTime();
//                if (SetFramechangeListener.tipsign == 0 || Window.everydaySign
//                        || (Window.Pattern == 1)) {
                    JTextPaneFont.insertDoc(JTextPaneFont.styledDoc,
                            String.valueOf(articleChars[n]), "灰", WatchingText());
//                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
    int heng;
    int number = 116;
    void changePosition() {// 自动滚动条翻页方法
        int fontSize = LocalConfig.fontSize;
        int pageCount = LocalConfig.typeCount;
        int hengsize = fontSize + 59; // 一个字横分辨率
        int shusize = fontSize + 14;// 一个字竖分辨率
        int shu = TypingAndWatching().getDividerLocation() / shusize; // 行数
        int tem = 0;
        heng = (TypingAndWatching().getWidth() - hengsize) / fontSize; // 一行字数
        number = number % (LocalConfig.typeCount + (heng + 1) / 3);
        if (thisPageNum == 0)
            while (thisPageTypeStr.length() > number) {
                if (shu > 2)
                    tem = (shu - 1) * (heng + 1);
                else
                    tem = 1 * (heng + 1);
                if (number + tem > pageCount)
                    number = pageCount - 1;
                else
                    number = number + tem;
                System.out.println("文章光标:" + number);
            }
        else
            while (thisPageTypeStr.length() + (heng + 1) / 3 > number) {
                if (shu > 2)
                    tem = (shu - 1) * (heng + 1);
                else
                    tem = 1 * (heng + 1);
                if (number + tem > pageCount)
                    number = pageCount + (heng + 1) / 3 - 1;
                else
                    number = number + tem;
                System.out.println("文章光标:" + number);
            }
        if (thisPageTypeStr.length() == 1) {
            if (shu > 1) {
                number = (shu - 1) * (heng + 1) + (heng + 1) / 3;
            } else if (shu <= 1)
                number = 1 * (heng + 1) - (heng + 1) / 3;
        } else if (thisPageTypeStr.length() == 0) {
            if (shu > 1) {
                number = (shu - 1) * (heng + 1) + (heng + 1) / 3;
            } else if (shu <= 1)
                number = 1 * (heng + 1) - (heng + 1) / 3;
            WatchingJSP().getVerticalScrollBar().setValue(0);
        }
        WatchingText().setCaretPosition(number);
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
