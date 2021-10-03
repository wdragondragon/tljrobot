package com.jdragon.tljrobot.tljutils.response.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.06.23 23:37
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageTable<E> extends AbstractPage<Table<E>> {

    private Table<E> table;

    private Boolean hasNextPage;

    private Boolean hasPreviousPage;

    @Override
    protected Table<E> getRecord() {
        return this.table;
    }

    public Boolean getHasNextPage() {
        return getCurrent() < getPages();
    }

    public Boolean getHasPreviousPage() {
        return getCurrent() > 1;
    }
}
