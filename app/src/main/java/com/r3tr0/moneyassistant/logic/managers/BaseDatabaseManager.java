package com.r3tr0.moneyassistant.logic.managers;

/**
 * Copyright 2018 Tarek Mohamed
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;

public abstract class BaseDatabaseManager implements IDatabaseManager<Cursor> {
    private boolean isOpened = false;
    protected Context context;
    protected String databaseName;
    protected static SQLiteDatabase database;

    public BaseDatabaseManager(Context context, String databaseName){
        this.databaseName = databaseName;
        this.context = context;
        createDatabase(databaseName);
    }

    @Override
    public synchronized void createDatabase(String databaseName) {
        if (!isOpened) {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
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
