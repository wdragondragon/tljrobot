package com.jdragon.tljrobot.client.utils.common;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;

public class ChangeTxtThread extends Thread{
	File more;
	String filename;
//	Changetxt(File file){
//		 more = file;
//	}
public ChangeTxtThread(String filename){
		this.filename = filename;
	}
	@Override
	public void run(){
		try {
			changetxt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JTextArea(),"转换失败2");
		}
	}
	public  void changetxt()throws IOException{
		File one = new File("编码文件/生成码表.txt");
		
//		more = new File(filename);
		Set<String> strlist = new TreeSet<>();
		boolean sign = true;
		StringBuilder all = new StringBuilder();
		String str;
		int length;
		int xuan;
		String temp;
		Timer comp = new Timer ();

			FileInputStream fis = new FileInputStream(filename); 
	        InputStreamReader read = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader  bufferRead = new BufferedReader(read);
			
			
			FileOutputStream fos = new FileOutputStream(one); 
	        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			BufferedWriter  bufferWriter = new BufferedWriter(writer);
			
//			LookChange look = new LookChange();
			comp.timeStart();
			while((str=bufferRead.readLine())!=null){
				if(str.contains("---")) {
					continue;
				}
				int i = str.indexOf('#');
				int n = str.indexOf('$');
				if(i!=-1) {
					if(str.toCharArray()[i+1]=='序'||str.toCharArray()[i+1]=='用'||str.toCharArray()[i+1]=='固') {
						str = str.substring(0, i);
					} else {
						continue;
					}
				} else if(n!=-1) {
					continue;
				}
				String[] splited = str.split("\\s+");
				length = splited[1].length();
				temp = splited[1];
				xuan = 2;
				if(strlist.contains(splited[1])) {
					sign = false;
				}
				while(strlist.contains(temp+ xuan)){
					xuan++;
					sign = false;
				}
				if(sign) {
					strlist.add(splited[1]);
				} else {
					strlist.add(splited[1]+ xuan);
				}
				if(length<4&&sign){
					str = splited[0]+"\t"+splited[1];
					str += "_"+"\r\n";
				}
				else if(length>=4&&sign){
					str = splited[0]+"\t"+splited[1];
					str += "\r\n";
				}
				else {
					str = splited[0]+"\t"+splited[1]+ xuan;
					str += "\r\n";
				}
				all.append(str);
				sign = true;
				comp.timeEnd();
				System.out.println(splited[0]+"\t"+str);
			}
			bufferWriter.write(all.toString());
			bufferWriter.flush();
			double time = comp.getSecond();
			JOptionPane.showMessageDialog(new JTextArea(),"转换成功,重新设置全码表生效词提、编码提示与理论码长\n生成码表位于:编码文件\n该次转换历时"+String.format("%.2f", time)+"秒");
			bufferWriter.close();
			read.close();
			fis.close();
	}
}
