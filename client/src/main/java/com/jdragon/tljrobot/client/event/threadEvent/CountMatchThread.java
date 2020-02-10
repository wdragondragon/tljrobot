package com.jdragon.tljrobot.client.event.threadEvent;


import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.listener.common.TypingListener;

public class CountMatchThread extends Thread {
	public void run(){
		while(!TypingState.typingState){
			try {
				sleep(1);
			} catch (InterruptedException e1) {}
			for(int i = 5;i>=0;i--){//双方准备后倒数
				try {
					sleep(1000);
					SwingSingleton.WatchingText().setText(String.valueOf(i));
				} catch (InterruptedException e) {System.out.println("倒数失败");}
			}
			ReplayEvent.start();
			TypingState.typingState = true;
			TypingState.dailyCompetition = true;
			TypingListener.getInstance().changeFontColor();
			TypingState.typingStart();
			System.out.println("赛文开始");
		}
	}
}
