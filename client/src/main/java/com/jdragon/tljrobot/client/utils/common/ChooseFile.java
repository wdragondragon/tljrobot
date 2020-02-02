package com.jdragon.tljrobot.client.utils.common;


import com.jdragon.tljrobot.client.config.LocalConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChooseFile implements ActionListener{
	public static String cizufilename = LocalConfig.codeTable;
	@Override
	public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub 
		cizufilename = getFileName();
		if(cizufilename==null)
			return;
		BetterTypingSingleton.setBetterTyping(cizufilename);
	}
	public static String getFileName(){
		JFileChooser jfc;
		File file;
		jfc=new JFileChooser();  
	    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
	    jfc.showDialog(new JLabel(), "选择");  
	    file=jfc.getSelectedFile();
	    if(file!=null){
		    if(file.isDirectory()){
		    	System.out.println("文件夹:"+file.getAbsolutePath());  
		    }else if(file.isFile()){  
		        System.out.println("文件:"+file.getAbsolutePath());  
		    }
		    String str = file.getAbsolutePath();
		    str = str.replace("\\", "/");
		    System.out.println("选择词码表:"+str);
			return str;
	    }
	    return null;
	}
}
