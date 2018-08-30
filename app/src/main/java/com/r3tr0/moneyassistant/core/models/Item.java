package com.r3tr0.moneyassistant.core.models;

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
