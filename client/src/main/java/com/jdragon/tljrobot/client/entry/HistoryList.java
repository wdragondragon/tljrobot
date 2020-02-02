package com.jdragon.tljrobot.client.entry;

import lombok.Data;

import java.util.List;

/**
 * Create by Jdragon on 2020.01.20
 */
@Data
public class HistoryList {
    private List<History> historyList;
    private int total;
    private int pages;
    private int pageNum;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

}
