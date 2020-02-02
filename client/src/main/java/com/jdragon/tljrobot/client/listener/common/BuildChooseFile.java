package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.utils.common.Changetxt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BuildChooseFile implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
        jfc.showDialog(new JLabel(), "选择");  
        File file=jfc.getSelectedFile();  
        if(file.isDirectory()){  
            System.out.println("文件夹:"+file.getAbsolutePath());  
        }else if(file.isFile()){  
            System.out.println("文件:"+file.getAbsolutePath());  
        }  
        String str = file.getAbsolutePath();
        System.out.println(jfc.getSelectedFile().getName());  
        Changetxt change = new Changetxt(str);
		change.start();
    }
}