package com.r3tr0.moneyassistant.logic.threadding;

import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.List;

public class GetAllWalletsTask extends BaseBackgroundTask {
    private WalletsDatabaseModel model;

    public GetAllWalletsTask(final WalletsDatabaseModel model) {
        super(new IUseCase<List<Wallet>>() {
            @Override
            public List<Wallet> processInBackground() {
                return model.getAllItems();
            }
        });
        this.model = model;
    }
}
