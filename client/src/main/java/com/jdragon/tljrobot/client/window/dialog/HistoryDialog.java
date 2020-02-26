package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.HistoryList;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.listener.common.HistoryTableListener;
import com.jdragon.tljrobot.client.listener.common.KeyboardInterceptorListener;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Create by Jdragon on 2020.01.20
 */
public class HistoryDialog {
    private HistoryDialog(){}
    private static MainFra mainFra = MainFra.getInstance();
    public static JDialog getInstance() {
        if(!UserState.loginState){
            JOptionPane.showMessageDialog(new JTextArea(),"请先登录");
            return null;
        }
        if (historyDialog == null) init();
        else{
            getTable(page);
        }
        historyDialog.setLocation(mainFra.getX()-mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4);
        return historyDialog;
    }
    private static JDialog historyDialog;
    private static JTable jtable;
    private static DefaultTableModel tableM;
    private static JScrollPane table;
    private static JButton prePageButton,nextPageButton,goFirstPageButton,goLastPageButton,thisPageNum,goOtherPageButton;
    private static JTextField goOtherPage;
    private static int page = 1,lastPage = 1;
    private static JPanel p = new JPanel();
    private static void init(){
        historyDialog = new JDialog(mainFra, "历史记录",
                Dialog.ModalityType.DOCUMENT_MODAL);
        setNew();
        addListener();
        setAllBounds();
        addAll();
        configure();

    }
    private static void setNew(){
        p = new JPanel();
        prePageButton = new JButton("上一页");
        nextPageButton = new JButton("下一页");
        goFirstPageButton = new JButton("跳到首页");
        goLastPageButton = new JButton("跳到尾页");
        thisPageNum = new JButton("");
        goOtherPageButton = new JButton("跳转");
        goOtherPage = new JTextField("1");

        prePageButton.setFont(SwingSingleton.tipFont());
        nextPageButton.setFont(SwingSingleton.tipFont());
        goFirstPageButton.setFont(SwingSingleton.tipFont());
        goLastPageButton.setFont(SwingSingleton.tipFont());
        thisPageNum.setFont(SwingSingleton.tipFont());
        goOtherPageButton.setFont(SwingSingleton.tipFont());

        newTable();
    }
    private static void setAllBounds(){
        p.setLayout(null);
        historyDialog.setBounds(mainFra.getX()-mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,1200,650);
        table.setBounds(0,0,historyDialog.getWidth()-10,historyDialog.getHeight()-70);
        prePageButton.setBounds(10, table.getY() + table.getHeight(), 120, 30);
        thisPageNum.setBounds(140, table.getY() + table.getHeight(), 120, 30);
        nextPageButton.setBounds(270, table.getY() + table.getHeight(), 120, 30);
        goOtherPage.setBounds(400, table.getY() + table.getHeight(), 60, 30);
        goOtherPageButton.setBounds(470, table.getY() + table.getHeight(), 60, 30);
        goFirstPageButton.setBounds(540, table.getY() + table.getHeight(), 100, 30);
        goLastPageButton.setBounds(650, table.getY() + table.getHeight(), 100, 30);
    }
    private static void addListener(){
        nextPageButton.addActionListener(e -> getTable(++page));
        prePageButton.addActionListener(e->getTable(--page));
        goFirstPageButton.addActionListener(e->getTable(page=1));
        goLastPageButton.addActionListener(e->getTable(page=lastPage));
        goOtherPageButton.addActionListener(e -> {
            int goPage = Integer.valueOf(goOtherPage.getText());
            if(goPage>lastPage||goPage<1) {
                JOptionPane.showMessageDialog(null, "页数越界");
            } else {
                getTable(goPage);
            }

        });
        goOtherPage.addKeyListener(new KeyboardInterceptorListener());
    }
    private static void addAll(){
        p.add(table);
        p.add(prePageButton);
        p.add(nextPageButton);
        p.add(goFirstPageButton);
        p.add(goLastPageButton);
        p.add(goOtherPage);
        p.add(goOtherPageButton);
        p.add(thisPageNum);
        historyDialog.add(p);
    }
    //创建表格
    private static void newTable(){
        Object[] name = { "","文章id","日期", "速度", "击键", "码长", "字数", "回改", "退格", "错字",
                "选重", "键准", "键法", "打词率", "时间(秒)", "段数" };
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
        jtable.addMouseListener(new HistoryTableListener(jtable));
        getTable(page);
    }
    public static void getTable(int page){
        clearTable();
        HistoryList historyListEntry = HistoryEvent.getHistoryByPage(page);
        List<History> historyList = historyListEntry.getHistoryList();
        int i = (page-1)*20+1;
        for(History history:historyList){
            //增加表格行
            Vector vRow = new Vector();
            vRow.add(i++);
            vRow.add(history.getArticleId());
            vRow.add(history.getTypeDate());
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
            vRow.add(history.getParagraph());
            tableM.addRow(vRow);
        }
        lastPage = historyListEntry.getPages();
        thisPageNum.setText(page +"/"+historyListEntry.getPages());
        nextPageButton.setEnabled(historyListEntry.isHasNextPage());
        prePageButton.setEnabled(historyListEntry.isHasPreviousPage());
    }
    public static void clearTable(){
        while (jtable.getRowCount()>0) {
            tableM.removeRow(0);
        }
    }
    //配置窗口
    private static void configure(){
        historyDialog.setLocationRelativeTo(null);//显示屏幕中央
        historyDialog.setResizable(false);
        historyDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //添加图标
        historyDialog.setIconImage(new ImageIcon("images//installer_repair_1.png").getImage());
    }
}
