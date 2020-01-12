package com.jdragon.tljrobot.tlj.service.serviceImpl;


import com.jdragon.tljrobot.tlj.service.BestTypingService;
import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;

import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.ShortCodeEntity;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.SimpleEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BestTypingServiceImpl implements BestTypingService {
    @Autowired
    private BetterTyping betterTyping;

    @Autowired
    private SimpleEntry simpleEntry;

    @Override
    public ShortCodeEntity readyCreate(String str) {
        return simpleEntry.readyCreate(str,betterTyping);
    }
}
