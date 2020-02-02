package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.tljutils.DateUtil;

import java.sql.Date;

/**
 * Create by Jdragon on 2020.02.02
 */
public class DateNumInit extends Thread {
    static Date date1, date2;
    public void run() {
        date1 = DateUtil.now();
        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            date2 = date1;
            date1 = DateUtil.now();
            if (date1.toString().equals(date2.toString())) continue;
            else { NumState.dateNum = 0; }
        }
    }
}