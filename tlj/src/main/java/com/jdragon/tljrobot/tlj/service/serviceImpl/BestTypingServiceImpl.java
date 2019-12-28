package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.jdragon.tljrobot.tlj.dto.ShortCode.CodeEntity;
import com.jdragon.tljrobot.tlj.dto.ShortCode.ShortCodeEntity;
import com.jdragon.tljrobot.tlj.service.BestTypingService;
import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
import com.jdragon.tljrobot.tljutils.compShortCode.SubscriptInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BestTypingServiceImpl implements BestTypingService {
    @Autowired
    BetterTyping betterTyping;


    private ShortCodeEntity shortCodeEntity;
    @Override
    public ShortCodeEntity readyCreate(String str) {
        if(shortCodeEntity==null||!shortCodeEntity.getArticle().equals(str)) {
            betterTyping.changecolortip(str);
            betterTyping.compalllength();
            shortCodeEntity = new ShortCodeEntity();
            shortCodeEntity.setArticle(str);
            shortCodeEntity.setCodeEntities(getSimpleEntity(betterTyping.getSubscriptInstances()));
            shortCodeEntity.setArticleCodes(betterTyping.getDingShowStr());
            shortCodeEntity.setCodeLenth(betterTyping.getDingalllength());
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
