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
