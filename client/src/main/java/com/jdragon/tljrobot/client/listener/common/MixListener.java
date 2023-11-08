package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by Jdragon on 2020.01.31
 */
public class MixListener implements ActionListener {
    private static MixListener mixListener = new MixListener();

    private MixListener() {
    }

    public static MixListener getInstance() {
        return mixListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        mixButton(e.getActionCommand());
    }

    public void mixButton(String model) {
        if (model.equals("该段乱序")) {
            Article article = Article.getArticleSingleton();
            if (TypingState.sendArticle == Constant.SEND_WORDS) {
                article.setArticle(mixstr(ArticleTreeListener.chouqubufenlist, ArticleTreeListener.wordNum));
            } else if (LocalConfig.textMode == Constant.TEXT_MODE_EN) {
                article.setArticle(englishMix(article.getArticle()));
            } else {
                article.setArticle(mix(article.getArticle()));
            }
            ReplayEvent.start();
        } else if (model.equals("全局乱序")) {
            ArticleTreeListener.all = mix(ArticleTreeListener.all);
            if (ArticleTreeListener.all == null) {
                return;
            }
            ArticleTreeListener.showContent();
        }
    }

    public static String mix(String str) {
        if (str == null) {
            return null;
        }
        String mix = "";
        List list = new ArrayList<>();
        char[] a = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            list.add(a[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < str.length(); i++) {
            mix = mix + (list.get(i));
        }
        return mix;
    }

    public static String mixstr(List chouqulist, int y) {
        Collections.shuffle(chouqulist);
        String str = "";
        for (int i = 0; i < (chouqulist.size() < y ? chouqulist.size() : y); i++) {
            str += chouqulist.get(i);
        }
        return str;
    }

    public static String englishMix(String str) {
        List<String> list = Arrays.asList(str.split(" "));
        Collections.shuffle(list);
        return list.stream().collect(Collectors.joining(" "));
    }
//    public static String EnglishMix(){
//        String str = "";
//        Collections.shuffle(EnlishRamdom.wordlist);
//        for(int i = 0;i<EnlishRamdom.wordlist.size();i++){
//            str+=EnlishRamdom.wordlist.get(i);
//            str+=" ";
//        }
//        System.out.println("wfvh:"+str);
//        return str.substring(0,str.length()-1);
//    }
}
