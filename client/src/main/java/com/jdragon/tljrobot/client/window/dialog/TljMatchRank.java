package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.entry.History2;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.tljutils.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Create by Jdragon on 2020.02.02
 */
public class TljMatchRank {
    private TljMatchRank(){}
    private static MainFra mainFra = MainFra.getInstance();
    public static JDialog getInstance() {
        if (tljMatchRankDialog==null)init();
        clearTable();
        request();
        tljMatchRankDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,855,500);
        return tljMatchRankDialog;
    }
    private static JDialog tljMatchRankDialog;
    public static JTable table;
    public static DefaultTableModel tableM;
    public static JScrollPane tableN;
    private static void init(){
        tljMatchRankDialog = new JDialog(mainFra, "每日赛文排行",
                Dialog.ModalityType.DOCUMENT_MODAL);
        Object name[]={"","用户名","速度","击键","码长","字数","回改","退格","错字","选重","键准","键法","打词率","时间(秒)"},a[][] = null;
        tableM = new DefaultTableModel(a,name) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableM);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tableN = new JScrollPane(table);

        tableN.setBounds(0,0,845,tljMatchRankDialog.getHeight()-40);

        tljMatchRankDialog.add(tableN);

    }
    private static void request(){
        List<History2> historyList = HistoryEvent.getTljMatchAchList(DateUtil.now());
        int i = 1;
        for(History2 history:historyList){
            //增加表格行
            Vector vRow = new Vector();
            vRow.add(i++);
            vRow.add(history.getUserName());
            vRow.add(history.getSpeed());
            vRow.add(history.getKeySpeed());
            vRow.add(history.getKeyLength());
            vRow.add(history.getNumber());
            vRow.add(history.getDeleteText());
            vRow.add(history.getDeleteNum());
            vRow.add(history.getMistake());
            vRow.add(history.getRepeatNum());
            vRow.add(history.getKeyAccuracy());
            vRow.add(history.getKeyMethod());
            vRow.add(history.getWordRate());
            vRow.add(history.getTime());
            tableM.addRow(vRow);
        }
        if(historyList.size()==0) {
            JOptionPane.showMessageDialog(null, "今日无成绩");
        }
    }
    public static void clearTable(){
        while (table.getRowCount()>0)
            tableM.removeRow(0);
    }
}
