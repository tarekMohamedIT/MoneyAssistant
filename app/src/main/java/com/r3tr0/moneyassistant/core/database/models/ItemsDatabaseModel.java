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
import android.util.Log;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;
import com.r3tr0.moneyassistant.core.interfaces.OnDatabaseRowReadListener;
import com.r3tr0.moneyassistant.core.models.Item;

import java.util.ArrayList;
import java.util.List;

import CSTime.DateTime;

/**
 * An extension of the {@link BaseDatabaseModel} class.
 * Used to communicate with the database to grab the items from the database.
 * The {@link ItemsDatabaseModel} uses {@link Item} as a parameter to return it
 * through it's methods.
 */

public class ItemsDatabaseModel extends BaseDatabaseModel<Item> {

    //An event listener for database row reading event.
    private OnDatabaseRowReadListener<Item> onDatabaseRowReadListener;

    /**
     * A constructor to the {@link BaseDatabaseModel} base class.
     *
     * @param manager The linked {@link IDatabaseManager} class.
     */
    public ItemsDatabaseModel(IDatabaseManager manager) {
        super(manager);
    }

    /**
     * A setter for the row reading event listener.
     * @param onDatabaseRowReadListener
     */
    public void setOnDatabaseRowReadListener(OnDatabaseRowReadListener onDatabaseRowReadListener) {
        this.onDatabaseRowReadListener = onDatabaseRowReadListener;
    }

    /**
     * The table creating method
     */
    @Override
    public void createTable() {
        manager.executeOrder("create table if not exists items (" +
                "itemID integer primary key AUTOINCREMENT," +
                "name varchar," +
                "quantity integer," +
                "price double," +
                "date varchar," +
                "walletID integer)");
    }

    /**
     * The table dropping method
     */
    @Override
    public void dropTable() {
        manager.executeOrder("drop table items");
    }

    /**
     * The new item adding method.
     * @param data The new data item.
     */
    @Override
    public void addNew(Item data) {

        DateTime testingDateTime = data.getPurchaseDate();
        testingDateTime.addMonths(-1);
        manager.executeOrder("insert into items (name, quantity, price, date, walletID) values (" +
                "'" + data.getName() + "'," +
                "" + data.getQuantity() + "," +
                "" + data.getPrice() + "," +
                "'" + testingDateTime.getDateTime(DateTime.DEFAULT_DATE_REVERSE).replace("/", "-") + "'," +
                "" + data.getWalletID() + ")");
    }

    /**
     *the deleting method by item id
     * @param id The ID of the required item to be deleted.
     */
    @Override
    public void delete(int id) {
        manager.executeOrder("delete * from items where itemID=" + id);
    }

    /**
     * A method that gets all the items from the table
     * @return A list of items from the table
     */
    @Override
    public List<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = (Cursor) manager.executeQuery("select * from items");

        while (cursor.moveToNext()){
            Item item = new Item(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(3)
                    , cursor.getInt(2)
                    , new DateTime(cursor.getString(4), DateTime.DEFAULT_DATE_REVERSE.replace("/", "-"))
                    , cursor.getInt(5));

            if (onDatabaseRowReadListener != null)
                onDatabaseRowReadListener.onRowRead(items, item);

            items.add(item);
        }
        return items;
    }

    /**
     * The item replacing method
     * @param id The ID of the required item
     * @param newData The new item's data
     */
    @Override
    public void replaceItem(int id, Item newData) {
        manager.executeOrder("update items set " +
                "name='" + newData.getName() + "'," +
                "quantity=" + newData.getQuantity() + "," +
                "price=" + newData.getPrice() + "," +
                "date='" + newData.getPurchaseDate().getDateTime(DateTime.DEFAULT_DATE_REVERSE).replace("/", "-") + "'," +
                "walletID=" + newData.getWalletID() +
                " where itemID=" + id);
    }

    /**
     * A method for clearing the table.
     */
    @Override
    public void clearAll() {
        manager.executeOrder("delete from items");
    }

    /**
     * A method for getting all items by it's wallet ID.
     * @param walletID The ID of the wallet.
     * @return A list of items from the table.
     */
    public List<Item> getAllItems(int walletID) {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = (Cursor) manager.executeQuery("select * from items where walletID=" + walletID);

        while (cursor.moveToNext()){
            items.add(new Item(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(3)
                    , cursor.getInt(2)
                    , new DateTime(cursor.getString(4), DateTime.DEFAULT_DATE_REVERSE.replace("/", "-"))
                    , cursor.getInt(5)));
        }

        return items;
    }

    /**
     * A method for getting all items by it's entry date using it's {@link DateTime}.
     * When given 0 to any of the 3 parameters, meaning it should get all of the items.
     *
     * @param years The year required to get items from
     * @param months The month required to get items from
     * @param days The day required to get items from
     * @return A list of items from the table.
     */
    public List<Item> getAllItems(int years, int months, int days) {
        String sql;

        if (years == 0)
            sql = "select * from items order by date";
        else if (months == 0)
            sql = "select * from items where date like '" + years + "%' order by date";
        else if (days == 0)
            sql = "select * from items where date like '" + years + "-" + String.format("%02d", months) + "%' order by date";
        else
            sql = "select * from items where date like '" + years + "-" + String.format("%02d", months) + "-" + String.format("%02d", days) + "' order by date";

        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = (Cursor) manager.executeQuery(sql);

        while (cursor.moveToNext()) {
            Item item = new Item(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(3)
                    , cursor.getInt(2)
                    , new DateTime(cursor.getString(4), DateTime.DEFAULT_DATE_REVERSE.replace("/", "-"))
                    , cursor.getInt(5));

            if (onDatabaseRowReadListener != null)
                onDatabaseRowReadListener.onRowRead(items, item);

            items.add(item);
        }
        return items;


    }

    /**
     * A method for getting all unique years in the table.
     * @return A list of strings containing the years.
     */
    public List<String> getAllYears() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("All");

        Cursor cursor = (Cursor) manager.executeQuery("select distinct (SUBSTR(date, 1, 4)) AS years FROM items ORDER BY years");

        while (cursor.moveToNext()) {
            strings.add(cursor.getString(0));
        }

        return strings;
    }

    /**
     * A method for getting all unique months inside a year.
     * @param years The year that the user requires months from.
     * @return A list of strings containing the months.
     */
    public List<String> getMonths(int years) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("All");
        String sql = "select distinct (SUBSTR(date, 6, 2)) AS months FROM items where date like '" + years + "%' ORDER BY months";
        Cursor cursor = (Cursor) manager.executeQuery(sql);

        while (cursor.moveToNext()) {
            strings.add(cursor.getString(0));
        }

        return strings;
    }

    /**
     * A method for getting all unique days inside a month of a year.
     * @param years The year that the user requires days from.
     * @param months The month that the user requires days from.
     * @return A list of strings containing the days.
     */
    public List<String> getDays(int years, int months) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("All");
        String sql = "select distinct (SUBSTR(date, 9, 2)) AS days FROM items where date like '" + years + "-" + String.format("%02d", months) + "%' ORDER BY days";
        Log.e("sql", sql);
        Cursor cursor = (Cursor) manager.executeQuery(sql);

        while (cursor.moveToNext()) {
            strings.add(cursor.getString(0));
        }

        return strings;
    }
}
