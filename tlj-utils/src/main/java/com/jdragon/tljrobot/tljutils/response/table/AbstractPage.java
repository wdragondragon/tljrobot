package com.jdragon.tljrobot.tljutils.response.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.06.23 23:38
 * @Description:
 */
@Data
@Accessors(chain = true)
public abstract class AbstractPage<T> {

    private Long total; // 总条数

    private Long size; // 页面大小

    private Long pages;  // 总页数

    private Long current; //当前页

//    public long getPages() {
//        if (pages == null) {
//            return (total + size - 1) / size;
//        }
//        return pages;
//    }

    @JsonIgnore
    protected abstract T getRecord();
}
