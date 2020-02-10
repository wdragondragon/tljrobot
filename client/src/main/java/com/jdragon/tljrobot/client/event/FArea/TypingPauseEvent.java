package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.component.SwingSingleton;

import javax.swing.*;

/**
 * Create by Jdragon on 2020.01.17
 */
public class TypingPauseEvent {
    public static void start(){
        if(TypingState.dailyCompetition){
            JOptionPane.showMessageDialog(null,"先结束日赛");
            return;
        }
        if(TypingState.pause) {
            TypingState.pause = false;
            TypingState.pauseEnd();
            SwingSingleton.TypingText().setEditable(true);

        }else{
            TypingState.pause = true;
            TypingState.pauseStart();
            SwingSingleton.TypingText().setEditable(false);
        }
    }
}
