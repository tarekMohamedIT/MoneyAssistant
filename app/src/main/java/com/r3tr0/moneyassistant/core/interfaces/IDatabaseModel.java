package com.r3tr0.moneyassistant.core.interfaces;

import java.util.List;

public interface IDatabaseModel<V> {

    /**
     * The table creating method
     */
    void createTable();

    /**
     *
     */
    void dropTable();

    /**
     * The new item adding method.
     *
     * @param data The new data item.
     */
    void addNew(V data);
    void delete(int id);
    List<V> getAllItems();
    void replaceItem(int id, V newData);
    void clearAll();
}
