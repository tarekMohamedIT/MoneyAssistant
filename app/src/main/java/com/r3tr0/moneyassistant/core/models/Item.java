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
import android.support.annotation.NonNull;

import CSTime.DateTime;

public class Item implements Parcelable, Comparable<Item>{
    private int id;
    private String name;
    private double price;
    private double quantity;
    private DateTime purchaseDate;
    private int walletID;

    public Item(int id, String name, double price, double quantity, int walletID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        purchaseDate = DateTime.getNow();
        this.walletID = walletID;
    }

    public Item(int id, String name, double price, double quantity, DateTime purchaseDate, int walletID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.walletID = walletID;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readDouble();
        purchaseDate = new DateTime(in.readLong());
        walletID = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public DateTime getPurchaseDate() {
        return purchaseDate;
    }

    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeDouble(quantity);
        dest.writeLong(purchaseDate.getTimeInMilliseconds());
        dest.writeInt(walletID);
    }

    @Override
    public int compareTo(@NonNull Item o) {
        return o.getPurchaseDate().compareTo(getPurchaseDate());
    }
}
