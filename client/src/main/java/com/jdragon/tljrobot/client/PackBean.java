package com.jdragon.tljrobot.client;

import java.io.Serializable;

/**
 * Create by Jdragon on 2020.02.13
 */
public class PackBean implements Serializable {
    private String packetType;
    private Object data;

    public String getPacketType() {
        return packetType;
    }
    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
