package com.r3tr0.moneyassistant.core.models;

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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A model class for the Wallets in the database to be used.
 */
public class Wallet implements Parcelable {
    private int walletID;
    private String name;
    private double totalMoney;
    private double remainingMoney;
    private double deletedMoney;
    private boolean isPrimary;

    public Wallet(int walletID, String name, double totalMoney, double remainingMoney, double deletedMoney, boolean isPrimary) {
        this.walletID = walletID;
        this.name = name;
        this.totalMoney = totalMoney;
        this.remainingMoney = remainingMoney;
        this.deletedMoney = deletedMoney;
        this.isPrimary = isPrimary;
    }

    public Wallet(String name, double totalMoney, double remainingMoney, double deletedMoney, boolean isPrimary) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.remainingMoney = remainingMoney;
        this.deletedMoney = deletedMoney;
        this.isPrimary = isPrimary;
    }

    protected Wallet(Parcel in) {
        walletID = in.readInt();
        name = in.readString();
        totalMoney = in.readDouble();
        remainingMoney = in.readDouble();
        deletedMoney = in.readDouble();
        isPrimary = in.readByte() != 0;
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public int getWalletID() {
        return walletID;
    }

    public String getName() {
        return name;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public double getRemainingMoney() {
        return remainingMoney;
    }

    public double getDeletedMoney() {
        return deletedMoney;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(walletID);
        dest.writeDouble(totalMoney);
        dest.writeDouble(remainingMoney);
        dest.writeDouble(deletedMoney);
        dest.writeByte((byte) (isPrimary? 1 : 0));
    }

    @Override
    public String toString() {
        return getName();
    }

    /*
    @Override
    public void addNew(Wallet data) {
        if (data.isPrimary()){
            executeOrder("update wallets set isPrimary=0 where walletID=(select walletID from wallets where isPrimary=1)");
        }

        executeOrder("insert into wallets (name, total, remaining, deleted, isPrimary) values (" +
                "'" + data.getName() + "'," +
                "" + data.getTotalMoney() + "," +
                "" + data.getRemainingMoney() + "," +
                "" + data.getDeletedMoney() + "," +
                "" + (data.isPrimary()? 1 : 0) + ")");
    }

    @Override
    public void delete(int id) {
        executeOrder("delete * from wallets where walletID=" + id);
    }

    @Override
    public List<Wallet> getAllItems() {
        List<Wallet> wallets = new ArrayList<>();
        Cursor cursor = executeQuery("select * from wallets");

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
            executeOrder("update wallets set isPrimary=0 where walletID=(select walletID from wallets where isPrimary=1)");
        }

        executeOrder("update wallets set " +
                "name='" + newData.getName() + "'," +
                "total=" + newData.getTotalMoney() + "," +
                "remaining=" + newData.getRemainingMoney() + "," +
                "deleted=" + newData.getDeletedMoney() + "," +
                "isPrimary=" + (newData.isPrimary()? 1 : 0) +
                " where walletID=" + id);
    }

    public Wallet getWalletByID(int id){
        Cursor cursor = executeQuery("select * from wallets where walletID=" + id);

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
        Cursor cursor = executeQuery("select * from wallets where name='" + name + "'");

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
        executeOrder("delete * from wallets");
    }
     */
}
