package com.jdragon.tljrobot.client.test.tplink;

import java.util.Arrays;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.03 15:02
 * @Description:
 */
public class HostInfo {
    private String[] plan_rule;
    private String ip;
    private int up_speed;
    private int down_limit;
    private int type;
    private int wifi_mode;
    private boolean is_cur_host;
    private String ssid;
    private String mac;
    private int down_speed;
    private String hostname;
    private int blocked;
    private int up_limit;

    public void setPlan_rule(String[] plan_rule) {
        this.plan_rule = plan_rule;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUp_speed(int up_speed) {
        this.up_speed = up_speed;
    }

    public void setDown_limit(int down_limit) {
        this.down_limit = down_limit;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setWifi_mode(int wifi_mode) {
        this.wifi_mode = wifi_mode;
    }

    public void setIs_cur_host(boolean is_cur_host) {
        this.is_cur_host = is_cur_host;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setDown_speed(int down_speed) {
        this.down_speed = down_speed;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public void setUp_limit(int up_limit) {
        this.up_limit = up_limit;
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "plan_rule=" + Arrays.toString(plan_rule) +
                ", ip='" + ip + '\'' +
                ", up_speed=" + up_speed +
                ", down_limit=" + down_limit +
                ", type=" + type +
                ", wifi_mode=" + wifi_mode +
                ", is_cur_host=" + is_cur_host +
                ", ssid='" + ssid + '\'' +
                ", mac='" + mac + '\'' +
                ", down_speed=" + down_speed +
                ", hostname='" + hostname + '\'' +
                ", blocked=" + blocked +
                ", up_limit=" + up_limit +
                '}';
    }
}
