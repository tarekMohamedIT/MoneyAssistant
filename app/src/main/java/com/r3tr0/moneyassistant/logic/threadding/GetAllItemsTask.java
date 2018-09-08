package com.r3tr0.moneyassistant.logic.threadding;

import android.util.Log;

import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.interfaces.OnDatabaseRowReadListener;
import com.r3tr0.moneyassistant.core.models.Item;

import java.util.List;

import CSTime.DateTime;

public class GetAllItemsTask extends BaseBackgroundTask {
    private ItemsDatabaseModel model;


    public GetAllItemsTask(ItemsDatabaseModel databaseModel, final Integer years, final Integer months, final Integer days) {
        super();
        this.model = databaseModel;
        setUseCase(new IUseCase<List<Item>>() {
            private DateTime currentDate;
            @Override
            public List<Item> processInBackground() {
                model.setOnDatabaseRowReadListener(new OnDatabaseRowReadListener() {
                    @Override
                    public void onRowRead(List<Item> items, Item item) {
                        Log.e("current date status", currentDate == null ? "is" : "isn't");//checking on the current date

                        if (currentDate == null //first time running
                                || currentDate.compareTo(item.getPurchaseDate()) != 0) { //comparing two times

                            items.add(new Item(0
                                    , ""
                                    , 0
                                    , 0
                                    , item.getPurchaseDate()
                                    , 0));

                            currentDate = new DateTime(item.getPurchaseDate().getTimeInMilliseconds());
                        }
                    }
                });

                List<Item> items = model.getAllItems(years, months, days);
                model.setOnDatabaseRowReadListener(null);
                currentDate = null;
                return items;
            }
        });
    }


}
