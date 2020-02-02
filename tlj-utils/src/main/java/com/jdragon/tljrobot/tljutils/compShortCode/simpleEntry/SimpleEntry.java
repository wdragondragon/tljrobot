package com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry;

import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
import com.jdragon.tljrobot.tljutils.compShortCode.SubscriptInstance;

public class SimpleEntry {
    private ShortCodeEntity shortCodeEntity;
    public ShortCodeEntity readyCreate(String str,BetterTyping betterTyping) {
        if(shortCodeEntity==null||!shortCodeEntity.getArticle().equals(str)) {
            betterTyping.changecolortip(str);
            betterTyping.compalllength();
            shortCodeEntity = new ShortCodeEntity();
            shortCodeEntity.setArticle(str);
            shortCodeEntity.setCodeEntities(getSimpleEntity(betterTyping.getSubscriptInstances()));
            shortCodeEntity.setArticleCodes(betterTyping.getDingShowStr());
            shortCodeEntity.setCodeLength(betterTyping.getDingalllength());
            shortCodeEntity.setArticleAverCodes(
                    Double.parseDouble(String.format("%.2f",((double)betterTyping.getDingalllength())/str.length())));
        }
        return shortCodeEntity;
    }
    public CodeEntity[] getSimpleEntity(SubscriptInstance[] subscriptInstances){
        CodeEntity[] codeEntities = new CodeEntity[subscriptInstances.length];
        int preWordsType = 0;//上一跳词型
        boolean bold = false;//上一跳加粗情况
        for(int i = 0;i<codeEntities.length;i++){
            int wordsSign = subscriptInstances[i].getNext();
            for(int j=i;j<=wordsSign;j++){
                CodeEntity codeEntity = new CodeEntity();
                codeEntity.setIndex(j);
                codeEntity.setWord(subscriptInstances[j].getWord());
                codeEntity.setWordCode(subscriptInstances[j].getWordCode());
                codeEntity.setType(subscriptInstances[j].getType());
                codeEntity.setNext(subscriptInstances[j].getNext());
                if(preWordsType!=0&&preWordsType==subscriptInstances[i].getType()){
                    codeEntity.setBold(!bold);
                }
                codeEntities[j] = codeEntity;
            }
            if(subscriptInstances[i].isUseWordSign()) {
                codeEntities[i].setWords(subscriptInstances[wordsSign]
                        .getShortCodePreInfo().getWords());

                codeEntities[i].setWordsCode(subscriptInstances[wordsSign]
                        .getShortCodePreInfo().getWordsCode());
            }
            preWordsType = codeEntities[i].getType();
            bold = codeEntities[i].isBold();
            i = wordsSign;
        }
        subscriptInstances = null;
        return codeEntities;
    }
}
