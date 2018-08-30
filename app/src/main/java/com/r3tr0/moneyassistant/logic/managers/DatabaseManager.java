package com.r3tr0.moneyassistant.logic.managers;

import android.content.Context;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseModel;

public class DatabaseManager
        extends BaseDatabaseManager{

    private IDatabaseModel model;

    public DatabaseManager(Context context) {
        super(context, "ma_db");
    }

    public void setModel(IDatabaseModel model) {
        this.model = model;
    }

    public IDatabaseModel getModel() {
        return model;
    }
}
