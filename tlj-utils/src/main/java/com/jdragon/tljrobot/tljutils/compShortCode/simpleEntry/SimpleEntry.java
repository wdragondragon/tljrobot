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
        }
        return shortCodeEntity;
    }
    public CodeEntity[] getSimpleEntity(SubscriptInstance[] subscriptInstances){
        CodeEntity[] codeEntities = new CodeEntity[subscriptInstances.length];
        for(int i = 0;i<codeEntities.length;i++){
            int wordsSign = subscriptInstances[i].getNext();
            for(int j=i;j<=wordsSign;j++){
                CodeEntity codeEntity = new CodeEntity();
                codeEntity.setWord(subscriptInstances[j].getWord());
                codeEntity.setWordCode(subscriptInstances[j].getWordCode());
                codeEntity.setType(subscriptInstances[i].getType());
                codeEntities[j] = codeEntity;
            }
            if(subscriptInstances[i].isUseWordSign()) {
                codeEntities[i].setWords(subscriptInstances[wordsSign].getShortCodePreInfo().getWords());
                codeEntities[i].setWordsCode(subscriptInstances[wordsSign].getShortCodePreInfo().getWordsCode());
            }
            i = wordsSign;
        }
        subscriptInstances = null;
        return codeEntities;
    }
}
