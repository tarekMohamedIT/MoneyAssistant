package com.r3tr0.moneyassistant.core.database.models;

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

import android.database.Cursor;

import com.r3tr0.moneyassistant.core.exceptions.wallets.WalletInsufficientBalanceException;
import com.r3tr0.moneyassistant.core.exceptions.wallets.WalletNotExistedException;
import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletsDatabaseModel extends BaseDatabaseModel<Wallet> {
    public WalletsDatabaseModel(IDatabaseManager manager) {
        super(manager);
    }

    @Override
    public void createTable() {
        manager.executeOrder("create table if not exists wallets (" +
                "walletID integer primary key AUTOINCREMENT," +
                "name varchar," +
                "total double," +
                "remaining double," +
                "deleted double," +
                "isPrimary boolean)");
    }

    @Override
    public void dropTable() {
        manager.executeOrder("drop table wallets");
    }

    @Override
    public void addNew(Wallet data) {
        if (data.isPrimary()){
            manager.executeOrder("update wallets set isPrimary=0 where walletID=(select walletID from wallets where isPrimary=1)");
        }

        manager.executeOrder("insert into wallets (name, total, remaining, deleted, isPrimary) values (" +
                "'" + data.getName() + "'," +
                "" + data.getTotalMoney() + "," +
                "" + data.getRemainingMoney() + "," +
                "" + data.getDeletedMoney() + "," +
                "" + (data.isPrimary()? 1 : 0) + ")");
    }

    @Override
    public void delete(int id) {
        manager.executeOrder("delete * from wallets where walletID=" + id);
    }

    @Override
    public List<Wallet> getAllItems() {
        List<Wallet> wallets = new ArrayList<>();
        Cursor cursor = (Cursor) manager.executeQuery("select * from wallets");

        while (cursor.moveToNext())
            wallets.add(new Wallet(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(2)
                    , cursor.getDouble(3)
                    , cursor.getDouble(4)
                    , (cursor.getInt(5) == 1)));

        return wallets;
    }

    @Override
    public void replaceItem(int id, Wallet newData) {
        if (newData.isPrimary()){
            manager.executeOrder("update wallets set isPrimary=0 where walletID=(select walletID from wallets where isPrimary=1)");
        }

        manager.executeOrder("update wallets set " +
                "name='" + newData.getName() + "'," +
                "total=" + newData.getTotalMoney() + "," +
                "remaining=" + newData.getRemainingMoney() + "," +
                "deleted=" + newData.getDeletedMoney() + "," +
                "isPrimary=" + (newData.isPrimary()? 1 : 0) +
                " where walletID=" + id);
    }

    public Wallet getWalletByID(int id){
        Cursor cursor = (Cursor) manager.executeQuery("select * from wallets where walletID=" + id);

        if (cursor.moveToNext())
            return new Wallet(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(2)
                    , cursor.getDouble(3)
                    , cursor.getDouble(4)
                    , (cursor.getInt(5) == 1));

        return null;
    }

    public Wallet getWalletByName(String name){
        Cursor cursor = (Cursor) manager.executeQuery("select * from wallets where name='" + name + "'");

        if (cursor.moveToNext())
            return new Wallet(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(2)
                    , cursor.getDouble(3)
                    , cursor.getDouble(4)
                    , (cursor.getInt(5) == 1));

        return null;
    }

    @Override
    public void clearAll() {
        manager.executeOrder("delete from wallets");
    }

    public void addMoney(int walletID, double amount){
        manager.executeOrder(String.format("update wallets set total=total+%s, remaining=remaining+%s where walletID=%s", amount, amount, walletID));
    }

    public void subtractMoney(int walletID, double amount){
        Wallet wallet = getWalletByID(walletID);

        if (wallet != null){
            if (amount <= wallet.getRemainingMoney())
                manager.executeOrder(String.format("update wallets set remaining=remaining-%s where walletID=%s", amount, walletID));
            else throw new WalletInsufficientBalanceException();
        }

        else
            throw new WalletNotExistedException();
    }


}
