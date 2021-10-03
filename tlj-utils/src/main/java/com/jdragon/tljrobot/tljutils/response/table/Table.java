package com.jdragon.tljrobot.tljutils.response.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.06.23 22:31
 * @Description:
 */
@Data
public class Table<E> {

    private List<Header> headers = new ArrayList<>();

    private List<E> bodies = new ArrayList<>();

    public void addHeader(String key,String label,String component,Integer width,Boolean merge){
        addHeader(new Header(key,label,component,width,merge));
    }
    public void addHeader(Header header){
        this.headers.add(header);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {

        private String key; // 表头字段名

        private String label; // 表头字段中文名

        private String component;// 组件类型

        private Integer width;

        private Boolean merge;
    }
}
