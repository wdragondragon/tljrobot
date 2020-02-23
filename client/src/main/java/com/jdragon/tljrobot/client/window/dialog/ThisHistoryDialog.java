package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.tljutils.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ThisHistoryDialog {
    private ThisHistoryDialog(){};
    private static MainFra mainFra = MainFra.getInstance();
    public static JDialog getInstance() {
        if(thisHistoryDialog==null){
            init();
        }
        thisHistoryDialog.setLocation(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4);
        return thisHistoryDialog;
    }
    private static JDialog thisHistoryDialog;
    private static JTable jtable;
    private static DefaultTableModel tableM;
    private static JScrollPane table;
//    private static JPanel p = new JPanel();
    private static List<History> historyList = new LinkedList<>();
    private static void init(){
        thisHistoryDialog = new JDialog(mainFra, "本次记录",
                Dialog.ModalityType.DOCUMENT_MODAL);
        newTable();
//        p.add(table);
        thisHistoryDialog.add(table);
        thisHistoryDialog.setSize(1000,600);
    }
    public static void initTable(){
        while (jtable.getRowCount()>0) {
            tableM.removeRow(0);
        }
        historyList.clear();
    }
    public static void addRow(){
        History history = HistoryEvent.getHistoryEntry();
        historyList.add(history);
        Vector vRow1 = new Vector();
        vRow1.add(historyList.size());
        vRow1.add(DateUtil.nowStringToS2());
        vRow1.add(history.getSpeed());
        vRow1.add(history.getKeySpeed());
        vRow1.add(history.getKeyLength());
        vRow1.add(history.getNumber());
        vRow1.add(history.getDeleteText());
        vRow1.add(history.getDeleteNum());
        vRow1.add(history.getMistake());
        vRow1.add(history.getRepeatNum());
        vRow1.add(history.getKeyAccuracy());
        vRow1.add(history.getKeyMethod());
        vRow1.add(history.getWordRate());
        vRow1.add(history.getTime());
        vRow1.add(history.getParagraph());
        tableM.addRow(vRow1);
        //移动光标
        int row = jtable.getRowCount() - 1;//这里获取的是最后一行，当然也可以根据不同的需要获取到不同的行
        double speed=0f,keySpeed=0f,keyLength=0f,keyAccuracy=0f,keyMethod=0f,wordRate=0f,time=0f;
        int number = 0,deleteText = 0,deleteNum = 0,mistake = 0,repeatNum = 0,paragraph=0;
        for(History history1:historyList) {
            speed += history1.getSpeed();
            keySpeed += history1.getKeySpeed();
            keyLength += history1.getKeyLength();
            number += history1.getNumber();
            deleteText += history1.getDeleteText();
            deleteNum += history1.getDeleteNum();
            mistake += history1.getMistake();
            repeatNum += history1.getRepeatNum();
            keyAccuracy += history1.getKeyAccuracy();
            keyMethod += history1.getKeyMethod();
            wordRate += history1.getWordRate();
            time += history1.getTime();
            paragraph++;
        }
        tableM.setValueAt(String.format("%.2f",speed/historyList.size()),0,2);
        tableM.setValueAt(String.format("%.2f",keySpeed/historyList.size()),0,3);
        tableM.setValueAt(String.format("%.2f",keyLength/historyList.size()),0,4);
        tableM.setValueAt(number,0,5);
        tableM.setValueAt(deleteText,0,6);
        tableM.setValueAt(deleteNum,0,7);
        tableM.setValueAt(mistake,0,8);
        tableM.setValueAt(repeatNum,0,9);
        tableM.setValueAt(String.format("%.2f",keyAccuracy/historyList.size()),0,10);
        tableM.setValueAt(String.format("%.2f",keyMethod/historyList.size()),0,11);
        tableM.setValueAt(String.format("%.2f",wordRate/historyList.size()),0,12);
        tableM.setValueAt(String.format("%.2f",time),0,13);
        tableM.setValueAt(paragraph,0,14);
//		table.setRowSelectionInterval(row, row); //显示当前成绩高亮
        table.scrollRectToVisible(jtable.getCellRect(row, 0, true));
    }
    private static void newTable(){
        Object[] name = { "","日期", "速度", "击键", "码长", "字数", "回改", "退格", "错字",
                "选重", "键准", "键法", "打词率", "时间(秒)", "段" };
        //不允许修改表格内容的同时能高亮
        tableM = new DefaultTableModel(null,name) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtable = new JTable(tableM);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table = new JScrollPane(jtable);
        tableM.addRow(new String[]{"0", DateUtil.now().toString(), "0", "0", "0", "0", "0", "0", "0",
                "0", "0", "0", "0", "0", "0"});
    }
}
