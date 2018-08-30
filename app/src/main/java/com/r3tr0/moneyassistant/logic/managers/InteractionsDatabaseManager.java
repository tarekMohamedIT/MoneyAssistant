package com.r3tr0.moneyassistant.logic.managers;

import android.content.Context;

import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.exceptions.wallets.WalletNotExistedException;
import com.r3tr0.moneyassistant.core.models.Item;
import com.r3tr0.moneyassistant.core.models.Wallet;

public class InteractionsDatabaseManager extends MultiModelDatabaseManager {
    public InteractionsDatabaseManager(Context context) {
        super(context);
        models.add(new ItemsDatabaseModel(this));
        models.add(new WalletsDatabaseModel(this));
    }

    public void addItemToWallet(Item item, int walletID){
        Wallet wallet = ((WalletsDatabaseModel)getModelByClass(WalletsDatabaseModel.class)).getWalletByID(walletID);
        if (wallet != null) {//wallet is found
            double totalPrice = item.getPrice() * item.getQuantity();
            ((WalletsDatabaseModel)getModelByClass(WalletsDatabaseModel.class)).subtractMoney(walletID, totalPrice);
            item.setWalletID(walletID);
            ((ItemsDatabaseModel)getModelByClass(ItemsDatabaseModel.class)).addNew(item);
        }

        else
            throw new WalletNotExistedException();
    }

}
