package com.jdragon.tljrobot.client.window;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Create by Jdragon on 2020.02.07
 */
public class KeyBoardFra extends JFrame {
    private static KeyBoardFra keyBoardFra = new KeyBoardFra();
    public static KeyBoardFra getInstance(){
        return keyBoardFra;
    }
    private JTextArea articleText = new JTextArea();
    public  KeyBoardFra(){
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setSize(90,30);
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        add(articleText);
    }
    public void setArticleText(String string){
        articleText.setText(string);
    }
    public void addArticleText(String string){
        articleText.setText(articleText.getText()+string);
    }
    List<Integer> regex = Arrays.asList(190,188,186,191,219,22149,50,51,52,53,54,55,56,57,48);
    public void addArticleText(char string){
        String article = articleText.getText();
        if(article.length()==4){
            articleText.setText(String.valueOf(string));
        }else if(article.length()>0&&regex.contains((int)string)){
            articleText.setText("");
        }else{
            articleText.setText(articleText.getText() + string);
        }
    }
    public void redArticleText(){
        String article = articleText.getText();
        if(article.length()>0){
            articleText.setText(article.substring(0,article.length()-1));
        }
    }
    public String  getArticleText(){
        return articleText.getText();
    }
}
