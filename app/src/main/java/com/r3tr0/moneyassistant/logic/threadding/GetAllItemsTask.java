package com.r3tr0.moneyassistant.logic.threadding;

import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.models.Item;

import java.util.List;

public class GetAllItemsTask extends BaseBackgroundTask {
    private ItemsDatabaseModel model;

    public GetAllItemsTask(final ItemsDatabaseModel model) {
        super(new IUseCase<List<Item>>() {
            @Override
            public List<Item> processInBackground() {
                return model.getAllItems();
            }
        });
        this.model = model;
    }
}
