package com.r3tr0.moneyassistant.core.database.models;

import android.database.Cursor;
import android.util.Log;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;
import com.r3tr0.moneyassistant.core.interfaces.OnDatabaseRowReadListener;
import com.r3tr0.moneyassistant.core.models.Item;

import java.util.ArrayList;
import java.util.List;

import CSTime.DateTime;

public class ItemsDatabaseModel extends BaseDatabaseModel<Item> {
    private OnDatabaseRowReadListener onDatabaseRowReadListener;

    public ItemsDatabaseModel(IDatabaseManager manager) {
        super(manager);
    }

    public OnDatabaseRowReadListener getOnDatabaseRowReadListener() {
        return onDatabaseRowReadListener;
    }

    public void setOnDatabaseRowReadListener(OnDatabaseRowReadListener onDatabaseRowReadListener) {
        this.onDatabaseRowReadListener = onDatabaseRowReadListener;
    }

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

    @Override
    public void dropTable() {
        manager.executeOrder("drop table items");
    }

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

    @Override
    public void delete(int id) {
        manager.executeOrder("delete * from items where itemID=" + id);
    }

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

    @Override
    public void clearAll() {
        manager.executeOrder("delete from items");
    }

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

    public List<String> getAllYears() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("All");

        Cursor cursor = (Cursor) manager.executeQuery("select distinct (SUBSTR(date, 1, 4)) AS years FROM items ORDER BY years");

        while (cursor.moveToNext()) {
            strings.add(cursor.getString(0));
        }

        return strings;
    }

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
