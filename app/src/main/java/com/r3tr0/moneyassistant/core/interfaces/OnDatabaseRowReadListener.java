package com.r3tr0.moneyassistant.core.interfaces;

import com.r3tr0.moneyassistant.core.models.Item;

import java.util.List;

public interface OnDatabaseRowReadListener {
    void onRowRead(List<Item> items, Item item);
}
