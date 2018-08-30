package com.r3tr0.moneyassistant.core.interfaces;

import java.util.List;

public interface IDatabaseModel<V> {

    void createTable();
    void dropTable();
    void addNew(V data);
    void delete(int id);
    List<V> getAllItems();
    void replaceItem(int id, V newData);
    void clearAll();
}
