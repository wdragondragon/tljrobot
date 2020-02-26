package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.TypingState.*;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;
import com.jdragon.tljrobot.client.utils.common.Timer;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.CodeEntity;
import lombok.Data;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;
import static com.jdragon.tljrobot.client.entry.TypingState.*;
@Data
public class TypingListener implements DocumentListener, KeyListener {
    private static TypingListener typingListener;
    public TypingListener(){}
    public static TypingListener getInstance(){
        if(typingListener==null) {
            typingListener = new TypingListener();
        }
        return typingListener;
    }
    public static boolean delaySendResultSign;//跟打标志，作延迟用
    String leftStr = "qazwsxedcrfvtgb", rightStr = ";/.,。，；、plokmijnuhy";
    String typeStr = "";
    String articleStr = "";
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
            if(LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)|| Article.getArticleSingleton().getArticle()==null) {
                return;
            }
//            if(TypingText().getText().length()==0)return;
            if (e.getKeyChar() != '\b') {
                typeStr = typingText().getText() + e.getKeyChar();
            } else {
                typeStr = typingText().getText();
            }
            articleStr = Article.getArticleSingleton().getArticle();

            typeChars = typeStr.toCharArray();
            articleChars = articleStr.toCharArray();
            /**
             * 增加已打字数
             */
            if (typeStr.length() > oldTypeStrLength) {
                if (articleChars[typeStr.length() - 1] == e.getKeyChar()) {
                    NumState.rightNum++;
                }else {
                    NumState.misNum++;
                }
                NumState.num++;
                NumState.dateNum++;
            }
            /**
             * 计算打词率
             */
            try {
                compTypingWords(e.getKeyChar());// 计算打词
            } catch (Exception ignored) {}
            mistake = 0; // 错误字数清零
            oldTypeStrLength = typeStr.length();// 计算当前打字框长度
            for (n = 0; n < typeStr.length(); n++) { // 统计错误字数，向文本框添加字体
                if (articleChars.length-1<n||typeChars[n] != articleChars[n]) {
                    mistake++;
//                    String mistakeStr = "\"" + articleChars[n] + "\"在第"
//                            + (n + 1) + "个字\n";
//                    mistakeList.add(mistakeStr);
//                    missign.add(n);
                }
            }
            /**
             * 改变字数框
             */
            updateNumShow();

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
            if(!TypingState.dailyCompetition&&typingState) {
                changeTipLabel(typeStr.length());
            }
            changePosition();// 文本自动翻页
        } catch (Exception ignored) {}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            if(LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
                return;
            }
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
//                System.out.println("退格+2");
                deleteNumber += 2;
                isContinuityDeleteText = false;
            }
        } catch (Exception ex) {
            System.out.println("跟打框无字2");
        }
    }
    /**
     * 计算退格，键法，击键记录
     */
    @Override
    public void keyReleased(KeyEvent e) {
        try {
            if(LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
                return;
            }
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
            if(LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)||Article.getArticleSingleton().getArticle()==null) {
                return;
            }
            typeStr = typingText().getText();
            articleStr = Article.getArticleSingleton().getArticle();
            typeLength = typeStr.length();
            if (!typingState&&typeLength > 0) {
                init();//打字状态初始化
                typingStart();// 计算第一键时间
                typingState = true; //标记已开始跟打
            }
            if(typeLength<1) {
                return;
            }
            String typingLastIndexWord = String.valueOf(typeStr.charAt(typeLength - 1));
            String articleLastIndexWord = String.valueOf(articleStr.charAt(articleStr.length() - 1)); // 取两文本最后一个字
            if (typeStr.length() == articleStr.length() && typingLastIndexWord.equals(articleLastIndexWord)
                    && !(LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN))) // 两文本长度相等且最后一字相同时执行
            {
                typingText().setEditable(false); // 设置不可打字状态
                delaySendResultSign = true;
            }
        } catch (Exception exp) {exp.printStackTrace();}
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
                    temp.append(articleChars[k]);
                }
                typeWordsNumTemp = 0; // 当前词长度清零
                WordsState wordsState =
                        typeWordsList.get(typeWordsList.size() - 1);
                if (wordsState.getWords().equals(temp.substring(0, 1))) // 单字对比
                {
                    typeWordsList.remove(typeWordsList.get(typeWordsList.size() - 1));
                }
                typeWordsList.add(new WordsState(getSpeed(),getKeySpeed(), compInstantaneousSpeed(), temp.toString(), typingWordsTime));
            } else {
                typeWordsList.add(new WordsState(getSpeed(),getKeySpeed(), compInstantaneousSpeed(),String.valueOf(c),timer.getSecond()));
            }
        }
    }
    /**
     * @Author: Jdragon on 2020.01.20 上午 12:32
     * @param: [index]
     * @return: void
     * @Description 根据跟打进度来改变词语提示框的内容
     */
    public void changeTipLabel(int index){
        CodeEntity codeEntity = Article.getArticleSingleton()
                .getShortCodeEntity().getCodeEntities()[index];
        StringBuilder tipStr = new StringBuilder();
        String word = codeEntity.getWord();
        String wordCode = codeEntity.getWordCode();
        String words = "",wordsCode = "";
        tipStr.append(word).append(":").append(wordCode);
        if(codeEntity.getWords()!=null) {
            words = codeEntity.getWords();
            wordsCode = codeEntity.getWordsCode();
            tipStr.append("  ").append(words).append(":").append(wordsCode);
        }
        tipsLabel().setText(tipStr.toString());// 单字编码提示更改
        int chineseLength = word.length()+words.length();//中文长度
        int englishLength = wordCode.length()+wordsCode.length();//英文长度
        int subWidth = chineseLength*12+(englishLength+4)*8- tipsLabel().getWidth();//用中英文长度来计算改变的提示宽度
        Layout.addSize(subWidth,0, tipsLabel());
        Layout.addLocation(subWidth,0, sendArticleLabel());
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
                    if (typeChars.length>n&&typeChars[n] != articleChars[n]) {
                        JTextPaneFont.insertDoc(
                                String.valueOf(articleChars[n]), "红");
                    } else {
                        JTextPaneFont.insertDoc(
                                String.valueOf(articleChars[n]), "黑");
                    }
                    System.out.println(articleChars[n]);
                }
            } catch (Exception e) {
                n = 0;
                System.out.println("wussssss");
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("跟打框无字3");
        }
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
//        if(thisPageNum==typeStr.length() / pageCount)return;
//        compPositionParams();
        articleStr = Article.getArticleSingleton().getArticle()!=null?Article.getArticleSingleton().getArticle():"";
        articleChars = articleStr.toCharArray();
        thisPageNum = typeStr.length() / pageCount;
        int lastIndex;
        if (articleStr.length() - pageCount * thisPageNum > pageCount) {
            lastIndex = (thisPageNum + 1) * pageCount + (widthFontNum + 1) / 3;
        } else {
            lastIndex = articleStr.length();
        }
        thisPageTypeStr = typeStr.substring(pageCount * thisPageNum);
        watchingText().setText(""); // 清空文本框
        try {
            if (thisPageNum > 0) {
                n = thisPageNum * pageCount - (widthFontNum + 1) / 3;
            } else {
                n = thisPageNum * pageCount;
            }

            for (; n < (Math.min(typeStr.length(), articleStr.length())); n++) { // 统计错误字数，向文本框添加字体
                if (typeChars[n] != articleChars[n] && typingState) {
                    JTextPaneFont.insertDoc(String.valueOf(articleChars[n]),"红");
                } else if (typingState) {
                    JTextPaneFont.insertDoc(String.valueOf(articleChars[n]),"黑");
                }
            }
        } catch (Exception e) {
            n = 0;
        }
        if (!typingState) {
            n = 0;
        }

        JTextPaneFont.createStyle("预读",
                LocalConfig.fontSize, false, false, false,
                LocalConfig.watchingBackgroundColor,LocalConfig.family, null);
        int readyFontNum = Integer.parseInt(SetDialog.readyFont.getText());
        if(n >= thisPageNum * (pageCount - 1)+readyFontNum){
            int tempReady = n + readyFontNum;
            if (tempReady > lastIndex) {
                tempReady = lastIndex;
            }
            for (; n < tempReady; n++) {
                JTextPaneFont.insertDoc(String.valueOf(articleChars[n]), "预读");
            }
        }
        MutableAttributeSet attrs = new SimpleAttributeSet();
        attrs.addAttribute("UnderlineOpen", true);
        attrs.addAttribute("Underline-Color", Color.red);
        CodeEntity[] codeEntities =
                Article.getArticleSingleton().getShortCodeEntity().getCodeEntities();
        for (; n < lastIndex; n++) { // 添加剩下字体
            if (n >= Article.getArticleSingleton().getShortCodeEntity().getArticle().length()) {
                break;
            }
            if (!LocalConfig.tip || TypingState.dailyCompetition
                    || LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)) {
                JTextPaneFont.insertDoc(String.valueOf(articleChars[n]), "灰");
            }else {
                int type = codeEntities[n].getType();
                boolean isBold = codeEntities[n].isBold();
                int next = codeEntities[n].getNext();
                StringBuilder insertStr = new StringBuilder();
                if(!isBold) {
                    switch (type) {
                        case 0:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "灰");
                            break;
                        case 1:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "绿");
                            break;
                        case 2:
                            String codes = codeEntities[n].getWordsCode();
                            if(codes!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number = codes.substring(codes.length() - 1);
                                attrs.addAttribute("Number", number);
                                JTextPaneFont.insertDoc(insertStr.toString(), "绿",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "绿斜");
                            }

                            break;
                        case 3:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "蓝");
                            break;
                        case 4:
                            String codes1 = codeEntities[n].getWordsCode();
                            if(codes1!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number1 = codes1.substring(codes1.length() - 1);
                                attrs.addAttribute("Number", number1);
                                JTextPaneFont.insertDoc(insertStr.toString(), "蓝",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "蓝斜");
                            }
                            break;
                        case 5:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "粉");
                            break;
                        case 6:
                            String codes2 = codeEntities[n].getWordsCode();
                            if(codes2!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number2 = codes2.substring(codes2.length() - 1);
                                attrs.addAttribute("Number", number2);
                                JTextPaneFont.insertDoc(insertStr.toString(), "粉",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "粉斜");
                            }
                            break;
                        default:break;
                    }
                }else{
                    switch (type) {
                        case 0:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "灰");
                            break;
                        case 1:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "绿粗");
                            break;
                        case 2:
                            String codes = codeEntities[n].getWordsCode();
                            if(codes!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number = codes.substring(codes.length() - 1);
                                attrs.addAttribute("Number", number);
                                JTextPaneFont.insertDoc(insertStr.toString(), "绿粗",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "绿粗斜");
                            }
                            break;
                        case 3:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "蓝粗");
                            break;
                        case 4:
                            String codes1 = codeEntities[n].getWordsCode();
                            if(codes1!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number1 = codes1.substring(codes1.length() - 1);
                                attrs.addAttribute("Number", number1);
                                JTextPaneFont.insertDoc(insertStr.toString(), "蓝粗",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "蓝粗斜");
                            }
                            break;
                        case 5:
                            for(int index = n;index<=next;index++) {
                                insertStr.append(articleChars[index]);
                            }
                            JTextPaneFont.insertDoc(insertStr.toString(), "粉粗");
                            break;
                        case 6:
                            String codes2 = codeEntities[n].getWordsCode();
                            if(codes2!=null) {
                                for (int index = n; index <= next; index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                String number2 = codes2.substring(codes2.length() - 1);
                                attrs.addAttribute("Number", number2);
                                JTextPaneFont.insertDoc(insertStr.toString(), "粉粗",attrs);
                            }else {
                                for(int index = n;index<=next;index++) {
                                    insertStr.append(articleChars[index]);
                                }
                                JTextPaneFont.insertDoc(insertStr.toString(), "粉粗斜");
                            }
                            break;
                        default:break;
                    }
                }
                n = next;
            }
        }
    }

    public void changeLookPlayFontColor(List<HashMap<String,Integer>> strList){
        watchingText().setText(""); // 清空文本框
        for(HashMap<String,Integer> hashMap:strList){
            for(Map.Entry<String,Integer> entry:hashMap.entrySet()){
                if(entry.getValue()==0){
                    JTextPaneFont.insertDoc( entry.getKey(), "对");
                }else if(entry.getValue()==1){
                    lookMiss++;
                    JTextPaneFont.insertDoc( entry.getKey(), "少");
                }else if(entry.getValue()==2){
                    lookMore++;
                    JTextPaneFont.insertDoc( entry.getKey(), "多");
                }else if(entry.getValue()==3){
                    lookMis++;
                    JTextPaneFont.insertDoc( entry.getKey(), "错");
                }else{
                    JTextPaneFont.insertDoc( entry.getKey(), "错原");
                }
            }
        }
        mistake = lookMis + lookMore + lookMiss;
    }
    public void changeListenPlayFontColor(List<HashMap<String,Integer>> strList){
        int length = 0;
        watchingText().setText(""); // 清空文本框
        for(HashMap<String,Integer> hashMap:strList){
            for(Map.Entry<String,Integer> entry:hashMap.entrySet()){
                length++;
                if(entry.getValue()==0){
                    JTextPaneFont.insertDoc( entry.getKey(), "对");
                }else if(entry.getValue()==1){
                    lookMiss++;
                    JTextPaneFont.insertDoc( entry.getKey(), "少");
                }else if(entry.getValue()==2){
                    lookMore++;
                    JTextPaneFont.insertDoc( entry.getKey(), "多");
                }else if(entry.getValue()==3){
                    lookMis++;
                    JTextPaneFont.insertDoc( entry.getKey(), "错");
                }else if(entry.getValue()==4){
                    JTextPaneFont.insertDoc( entry.getKey(), "错原");
                }else{
                    JTextPaneFont.insertDoc( entry.getKey(), "忽略");
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
    int widthFontNum,heightFontNum;// 一行字数,行数
    int cursor = 116;//光标所在位置
    int maxPageNum;
    int pageCount,fontSize;
    public void changePosition() {// 自动滚动条翻页方法
        int temp;
        fontSize = LocalConfig.fontSize;
        pageCount = LocalConfig.typePageCount;
        int fontWidth = fontSize + 59; // 一个字横分辨率
        int fontHeight = fontSize + 14+fontSize/2;// 一个字竖分辨率
        heightFontNum  = typingAndWatching().getDividerLocation() / fontHeight; // 行数
        widthFontNum = (typingAndWatching().getWidth() - fontWidth) / fontSize;//行字数
        cursor = cursor % (LocalConfig.typePageCount + (widthFontNum + 1) / 3);
        maxPageNum = (heightFontNum-1) * (widthFontNum + 1);
        if (thisPageNum == 0) {
            while (thisPageTypeStr.length()+maxPageNum/2 > cursor&&cursor!=pageCount-1) {
                if (heightFontNum > 2) {
                    temp = widthFontNum;
                } else {
                    temp = widthFontNum + 1;
                }
                if (cursor + temp > pageCount) {
                    cursor = pageCount - 1;
                } else {
                    cursor = cursor + temp;
                }
            }
        }else {
            while (thisPageTypeStr.length() +maxPageNum/2 + (widthFontNum + 1) / 3 > cursor&&cursor!=pageCount + (widthFontNum + 1) / 3 - 1) {
                if (heightFontNum > 2) {
                    temp = widthFontNum;
                } else {
                    temp = widthFontNum + 1;
                }
                if (cursor + temp > pageCount) {
                    cursor = pageCount + (widthFontNum + 1) / 3 - 1;
                } else {
                    cursor = cursor + temp;
                }
            }
        }
        if (thisPageTypeStr.length() == 1) {
            if (heightFontNum > 1) {
                cursor = maxPageNum/2 + (widthFontNum + 1) / 3;
            } else {
                cursor = (widthFontNum + 1)/2 - (widthFontNum + 1) / 3;
            }
        } else if (thisPageTypeStr.length() == 0) {
            if (heightFontNum > 1) {
                cursor = maxPageNum/2+ (widthFontNum + 1) / 3;
            } else {
                cursor = (widthFontNum + 1)/2 - (widthFontNum + 1) / 3;
            }
            watchingJsp().getVerticalScrollBar().setValue(0);
        }
        try{
            watchingText().setCaretPosition(cursor);
        }catch (Exception ignored){}

    }
    public void updateNumShow(){
        numberLabel().setText("字数:" + articleStr.length() + "/已打:" + typeStr.length() + "/错:"
                + mistake);

        numberRecordLabel().setText("总:" + NumState.num + " 对:"
                + NumState.rightNum + " 错:"
                + NumState.misNum + " 今:"
                + NumState.dateNum);
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
