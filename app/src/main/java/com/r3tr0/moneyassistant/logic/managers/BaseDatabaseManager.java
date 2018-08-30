package com.r3tr0.moneyassistant.logic.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;

public abstract class BaseDatabaseManager implements IDatabaseManager<Cursor> {
    private boolean isOpened = false;
    protected Context context;
    protected String databaseName;
    protected SQLiteDatabase database;

    public BaseDatabaseManager(Context context, String databaseName){
        this.databaseName = databaseName;
        this.context = context;
        createDatabase(databaseName);
    }

    @Override
    public synchronized void createDatabase(String databaseName) {
        if (!isOpened) {
            database = context.openOrCreateDatabase(databaseName,Context.MODE_PRIVATE, null);
            isOpened = true;
        }
    }

    @Override
    public synchronized void executeOrder(String sql) {
        database.execSQL(sql);
    }

    @Override
    public synchronized Cursor executeQuery(String sql) {
        return database.rawQuery(sql, null);
    }

    @Override
    public synchronized void closeConnection() {
        database.close();
        isOpened = false;
    }

}
