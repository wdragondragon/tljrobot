package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.entry.Article;

public class ArticleRegex {
    public static Article regexStringToArticle(String ArticleStr){
        int gang = 0,space = 0;
        int i,j,k,sign1=0,sign2=0;

        StringBuilder paragraphStr = new StringBuilder();
        StringBuilder title = new StringBuilder();
        char [] articleChars = ArticleStr.toCharArray();
        int length = ArticleStr.length();

        for(j=length-1;j>0;j--){
            if(articleChars[j]=='第'){
                for(k=j-1;k>=j-5;k--){
                    if(articleChars[k]=='-'){gang++;}
                }
                if(gang>=5){
                    sign1 = k;
                    space = 0;
                    paragraphStr = new StringBuilder();
                    while(articleChars[j]!='段'){
                        paragraphStr.append(articleChars[j]);j++;
                    }
                }
                gang=0;
            }
            if(sign1!=0) {
                break;
            }
        }
        for(j=sign1;j>0;j--){
            if(articleChars[j]=='\n')
            {
                space++;
                articleChars[j]='#';
                if(space>=2){
                    sign2  = j;
                    while(articleChars[j]!='\n'&&j>0) {
                        j--;
                    }
                    if(j>0) {
                        articleChars[j]='#';
                    } else {
                        j=-1;
                    }
                    for(j=j+1;j<sign2;j++) {
                        title.append(articleChars[j]);
                    }
                    break;}
            }
        }
        for(i=0;i<sign2;i++) {
            articleChars[i]='#';
        }
        for(i=sign1;i<length;i++) {
            articleChars[i]='#';
        }
        
        ArticleStr = String.valueOf(articleChars);
        String regex = "[^0123456789]+";
        ArticleStr = ArticleStr.replaceAll("#","");
//        ArticleStr = ArticleStr.replaceAll("\r","");
        paragraphStr = new StringBuilder(paragraphStr.toString().replaceAll(regex, ""));
        if(paragraphStr.length()>0){
            int paragraphNum = Integer.parseInt(paragraphStr.toString());
            System.out.println("段号"+paragraphNum+",标题:"+title+"\n"+ArticleStr);
            return Article.getArticleSingleton(paragraphNum, title.toString().replaceAll("\r",""),ArticleStr);
        }else {
            return null;
        }

    }

}
