package com.jdragon.tljrobot.client.entry;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.utils.common.BetterTypingSingleton;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.ShortCodeEntity;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.SimpleEntry;
import lombok.Data;

@Data
public class Article {
    private static Article articleSingleton = new Article();

    public static Article getArticleSingleton() {
        return articleSingleton;
    }
    public static Article getArticleSingleton(int paragraph1,String title1,String article1){
        if(LocalConfig.replace) {
            article1 = ArticleUtil.replace(article1);
        }
        if(LocalConfig.clearSpace) {
            article1 = ArticleUtil.clearSpace(article1);
        }
        getArticleSingleton().setArticleSingleton(paragraph1,title1,article1);
        return articleSingleton;
    }
    private void setArticleSingleton(int paragraph1,String title1,String article1){
        this.paragraph = paragraph1;
        this.title = title1;
        this.article = article1;
        this.shortCodeEntity = new SimpleEntry().readyCreate(article,BetterTypingSingleton.getInstance());
    }
    private int paragraph;
    private String title;
    private String article;
    private ShortCodeEntity shortCodeEntity;
    private Article(){}
    @Override
    public String toString() {
        return "[\n段落:"+paragraph+",\n标题:"+title+",\n文章:"+article+",\n编码详情:"+shortCodeEntity.toString()+"]";
    }
    public void setArticle(String article){
        this.article =article;
        this.shortCodeEntity = new SimpleEntry().readyCreate(article,BetterTypingSingleton.getInstance());
    }
    public void addParagraph(){
        paragraph++;
    }
}
