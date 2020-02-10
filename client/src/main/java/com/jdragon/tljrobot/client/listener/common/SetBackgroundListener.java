package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.jdragon.tljrobot.client.component.SwingSingleton.TypingText;
import static com.jdragon.tljrobot.client.component.SwingSingleton.WatchingText;
import static com.jdragon.tljrobot.client.config.LocalConfig.*;
public class SetBackgroundListener implements ActionListener {
	Dialog win = SetDialog.getInstance();
	Color tempColor;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="打字框背景颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.white);
			if(tempColor!=null) {
				TypingText().setBackground(tempColor);
				typingBackgroundColor = tempColor;
			}
		}
		else if(e.getActionCommand()=="文本框背景颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.white);
			if(tempColor!=null) {
				WatchingText().setBackground(tempColor);
				watchingBackgroundColor = tempColor;
			}
		}
		else if(e.getActionCommand()=="打对字颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.gray);
			if(tempColor!=null) {
				rightColor = tempColor;
				JTextPaneFont.createStyle("黑", typeDocName, fontSize, 0, 0, 0, Color.BLACK, family, rightColor);
			}
		}
		else if(e.getActionCommand()=="打错字颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.red);
			if(tempColor!=null)
				mistakeColor = tempColor;
				JTextPaneFont.createStyle("红", typeDocName, fontSize, 0, 0, 0, Color.BLACK, family, mistakeColor);
		}
		else if(e.getActionCommand()=="整体界面颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.red);
			if(tempColor!=null) {
				UIBackgroundColor = tempColor;
				MainFra.getInstance().getContentPane().setBackground(UIBackgroundColor);
			}
		}
		else if(e.getActionCommand()=="全码词颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板",new Color(128,138,135));
			if(tempColor!=null){
				fourCodeColor = tempColor;
				JTextPaneFont.createStyle("绿粗", typeDocName, fontSize, 1, 0, 0, fourCodeColor, family, mistakeColor);//GRAY
				JTextPaneFont.createStyle("绿", typeDocName, fontSize, 0, 0, 0, fourCodeColor, family, mistakeColor);//GRAY
				JTextPaneFont.createStyle("绿斜", typeDocName, fontSize, 0, 1, 0, fourCodeColor, family, mistakeColor);//GRAY
				JTextPaneFont.createStyle("绿粗斜", typeDocName, fontSize, 1, 1, 0, fourCodeColor, family, mistakeColor);//GRAY
			}	
		}
		else if(e.getActionCommand()=="二码词颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板", Color.ORANGE);
			if(tempColor!=null){
				twoCodeColor  = tempColor;
				JTextPaneFont.createStyle("粉粗", typeDocName, fontSize, 1, 0, 0, twoCodeColor, family, mistakeColor);//GRAY
				JTextPaneFont.createStyle("粉", typeDocName, fontSize, 0, 0, 0, twoCodeColor, family, mistakeColor);///GRAY
				JTextPaneFont.createStyle("粉斜", typeDocName, fontSize, 1, 0, 0, twoCodeColor, family, mistakeColor);///GRAY
				JTextPaneFont.createStyle("粉粗斜", typeDocName, fontSize, 1, 0, 0, twoCodeColor, family, mistakeColor);///GRAY
			}
				
		}
		else if(e.getActionCommand()=="三码词颜色"){
			tempColor = JColorChooser.showDialog(win, "调色板",Color.BLUE);
			if(tempColor!=null){
				threeCodeColor  = tempColor;
				JTextPaneFont.createStyle("蓝粗", typeDocName, fontSize, 1, 0, 0, threeCodeColor, family, mistakeColor);//GRAY
				JTextPaneFont.createStyle("蓝", typeDocName, fontSize, 0, 0, 0, threeCodeColor, family, mistakeColor);///GRAY
				JTextPaneFont.createStyle("蓝斜", typeDocName, fontSize, 1, 0, 0, threeCodeColor, family, mistakeColor);///GRAY
				JTextPaneFont.createStyle("蓝粗斜", typeDocName, fontSize, 1, 0, 0, threeCodeColor, family, mistakeColor);///GRAY//GRAY
			}
		}
	}
}