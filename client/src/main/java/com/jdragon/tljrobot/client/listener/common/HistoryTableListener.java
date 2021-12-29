package com.jdragon.tljrobot.client.listener.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.ArticleDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.window.dialog.HistoryDialog;
import com.jdragon.tljrobot.client.window.dialog.ShowArticleDialog;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Create by Jdragon on 2020.01.20
 */
public class HistoryTableListener implements MouseListener {

    private final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    JTable jTable;

    public HistoryTableListener(JTable jTable) {
        this.jTable = jTable;
    }

    private JPopupMenu m_popupMenu;

    private void createPopupMenu() {
        m_popupMenu = new JPopupMenu();
        JMenuItem replay = new JMenuItem("重打该段");
        JMenuItem getArticle = new JMenuItem("查看文章");


        replay.addActionListener(e -> {
            int selectedRow = jTable.getSelectedRow();
            String articleId = String.valueOf(jTable.getValueAt(selectedRow, 1));
            int paragraph = (int) jTable.getValueAt(selectedRow, 15);
            ArticleDto articleJson = getArticleJson(articleId, paragraph);
            if (articleJson != null) {
                Article.getArticleSingleton(paragraph, articleJson.getTitle(), articleJson.getContent());
                HistoryDialog.getInstance().setVisible(false);
                ReplayEvent.start();
            }
        });

        getArticle.addActionListener(e -> {
            int selectedRow = jTable.getSelectedRow();
            String articleId = String.valueOf(jTable.getValueAt(selectedRow, 1));
            int paragraph = (int) jTable.getValueAt(selectedRow, 15);
            ArticleDto articleJson = getArticleJson(articleId, paragraph);
            if (articleJson != null) {
                ShowArticleDialog.getInstance(articleJson.getContent()).setVisible(true);
            }
        });
        m_popupMenu.add(replay);
        m_popupMenu.add(getArticle);
    }

    private ArticleDto getArticleJson(String articleId, int paragraph) {
        if (paragraph == 0) {
            JOptionPane.showMessageDialog(null, "0段无法再次获取");
            return null;
        }
        return accountApi.getArticleById(Integer.valueOf(articleId));
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent e) {
        mouseRightButtonClick(e);
    }

    private void mouseRightButtonClick(java.awt.event.MouseEvent e) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = jTable.rowAtPoint(e.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            //将表格所选项设为当前右键点击的行
            jTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            m_popupMenu.show(jTable, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        createPopupMenu();
        jTable1MouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
