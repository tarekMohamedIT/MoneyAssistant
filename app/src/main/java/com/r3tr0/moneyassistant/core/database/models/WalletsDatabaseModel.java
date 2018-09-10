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

/**
 * An extension of the {@link BaseDatabaseModel} class.
 * Used to communicate with the database to grab the items from the database.
 * The {@link WalletsDatabaseModel} uses {@link Wallet} as a parameter to return it
 * through it's methods.
 */
public class WalletsDatabaseModel extends BaseDatabaseModel<Wallet> {

    /**
     * A constructor to the {@link BaseDatabaseModel} base class.
     *
     * @param manager The linked {@link IDatabaseManager} class.
     */
    public WalletsDatabaseModel(IDatabaseManager manager) {
        super(manager);
    }

    /**
     * The table creating method
     */
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

    /**
     * The table dropping method
     */
    @Override
    public void dropTable() {
        manager.executeOrder("drop table wallets");
    }

    /**
     * The new wallet adding method.
     *
     * @param data The new wallet's data.
     */
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

    /**
     *the deleting method by wallet id
     * @param id The ID of the required wallet to be deleted.
     */
    @Override
    public void delete(int id) {
        manager.executeOrder("delete * from wallets where walletID=" + id);
    }

    /**
     * A method that gets all the wallets from the table
     * @return A list of wallets from the table
     */
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

    /**
     * The wallet replacing method
     * @param id The ID of the required wallet
     * @param newData The new wallet's data
     */
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

    /**
     * A method that gets a wallet from the table by it's id.
     * @param id The ID of the wallet.
     * @return A wallet object from the table.
     */
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

    /**
     * A method that gets a wallet from the table by it's name.
     * @param name The name of the wallet.
     * @return A wallet object from the table.
     */
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

    /**
     * A method for clearing the table.
     */
    @Override
    public void clearAll() {
        manager.executeOrder("delete from wallets");
    }

    /**
     * Adds balance to a wallet by it's ID.
     * @param walletID The ID of the wallet.
     * @param amount The amount of money to be added to the wallet.
     */
    public void addMoney(int walletID, double amount){
        manager.executeOrder(String.format("update wallets set total=total+%s, remaining=remaining+%s where walletID=%s", amount, amount, walletID));
    }

    /**
     * Subtracts balance from a wallet by it's ID.
     * @param walletID The ID of the wallet.
     * @param amount The amount of money to be removed from the wallet.
     */
    public void subtractMoney(int walletID, double amount){
        Wallet wallet = getWalletByID(walletID);

        if (wallet != null){
            if (amount <= wallet.getRemainingMoney())
                manager.executeOrder(String.format("update wallets set remaining=remaining-%s where walletID=%s", amount, walletID));
            else throw new WalletInsufficientBalanceException();
        } else
            throw new WalletNotExistedException();
    }


}
