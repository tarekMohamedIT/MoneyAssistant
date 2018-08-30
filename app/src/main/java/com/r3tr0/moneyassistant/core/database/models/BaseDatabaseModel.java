package com.r3tr0.moneyassistant.core.database.models;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;
import com.r3tr0.moneyassistant.core.interfaces.IDatabaseModel;

public abstract class BaseDatabaseModel<T> implements IDatabaseModel<T> {
    IDatabaseManager manager;

    public BaseDatabaseModel(IDatabaseManager manager){
        this.manager = manager;
    }

    public void setManager(IDatabaseManager manager) {
        this.manager = manager;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        manager = null;
    }
}
