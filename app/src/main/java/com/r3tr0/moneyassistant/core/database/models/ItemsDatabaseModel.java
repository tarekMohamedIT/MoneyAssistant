package com.r3tr0.moneyassistant.core.database.models;

import android.database.Cursor;

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
                "date long," +
                "walletID integer)");
    }

    @Override
    public void dropTable() {
        manager.executeOrder("drop table items");
    }

    @Override
    public void addNew(Item data) {

        manager.executeOrder("insert into items (name, quantity, price, date, walletID) values (" +
                "'" + data.getName() + "'," +
                "" + data.getQuantity() + "," +
                "" + data.getPrice() + "," +
                "" + data.getPurchaseDate().getTimeInMilliseconds() + "," +
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
                    , new DateTime(cursor.getLong(4))
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
                "date=" + newData.getPurchaseDate().getTimeInMilliseconds() + "," +
                "walletID=" + newData.getWalletID() +
                " where itemID=" + id);
    }

    @Override
    public void clearAll() {
        manager.executeOrder("delete from items");
    }

    public List<Item> getItemsByWalletID(int walletID){
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = (Cursor) manager.executeQuery("select * from items where walletID=" + walletID);

        while (cursor.moveToNext()){
            items.add(new Item(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getDouble(3)
                    , cursor.getInt(2)
                    , new DateTime(cursor.getLong(4))
                    , cursor.getInt(5)));
        }

        return items;
    }
}
