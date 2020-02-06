package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.utils.common.Changetxt;
import com.jdragon.tljrobot.client.utils.common.ChooseFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildChooseFile implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub
        String str = ChooseFile.getFileName();
        if(str==null)return;
        Changetxt change = new Changetxt(str);
		change.start();
    }
}