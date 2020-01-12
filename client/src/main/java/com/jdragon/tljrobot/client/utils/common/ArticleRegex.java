package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.entry.Article;

public class ArticleRegex {
    public static Article regexStringToArticle(String ArticleStr){
        int gang = 0,space = 0;
        int i,j,k,sign1=0,sign2=0;

        String paragraphStr = new String();
        String title = new String();
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
                    paragraphStr = "";
                    while(articleChars[j]!='段'){
                        paragraphStr = paragraphStr+articleChars[j];j++;
                    }
                }
                gang=0;
            }
            if(sign1!=0)
                break;
        }
        for(j=sign1;j>0;j--){
            if(articleChars[j]=='\n')
            {
                space++;
                articleChars[j]='#';
                if(space>=2){
                    sign2  = j;
                    while(articleChars[j]!='\n'&&j>0)
                        j--;
                    if(j>0)
                        articleChars[j]='#';
                    else
                        j=-1;
                    for(j=j+1;j<sign2;j++)
                        title+=String.valueOf(articleChars[j]);
                    break;}
            }
        }
        for(i=0;i<sign2;i++)
            articleChars[i]='#';
        for(i=sign1;i<length;i++)
            articleChars[i]='#';
        
        ArticleStr = String.valueOf(articleChars);
        String regex = "[^0123456789]+";
        ArticleStr = ArticleStr.replaceAll("#","");
        ArticleStr = qukong(ArticleStr);
        ArticleStr = huanfu(ArticleStr);

        paragraphStr = paragraphStr.replaceAll(regex,"");

        if(paragraphStr.length()>0){
            int paragraphNum = Integer.valueOf(paragraphStr);
            System.out.println("段号"+paragraphNum+",标题:"+title+"\n"+ArticleStr);
            Article article = Article.getArticleSingleton(paragraphNum,title,ArticleStr);
            return article;
        }else return null;

    }
    public static String huanfu(String str){
        String initchar = ";:,.!?";
        String afterchar = "；：，。！？";
        char []a = str.toCharArray();
        int b ;
        char y[] = afterchar.toCharArray();
        for(int i =0;i<a.length;i++)
            if((b = initchar.indexOf(a[i]))!=-1)
                a[i] = y[b];
        str = String.valueOf(a);
        return str;
    }
    public static String qukong(String str){
        str = str.replaceAll("\\s*", "");
        str = str.replaceAll("　","");
        return str;
    }
    public static String biaoding(String str){
        char []a = str.toCharArray();
        String ding = "，。、！?↓,.!?";
        for(int i=0;i<a.length-1;i++){
            if(a[i]=='_'&&ding.indexOf(String.valueOf(a[i+1]))!=-1){
                a[i]='#';
            }
        }
        str = new String(a);
        str = str.replaceAll("#","");
        return str;
    }
}
