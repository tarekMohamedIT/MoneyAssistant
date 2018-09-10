package com.r3tr0.moneyassistant.logic.threadding;

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
                model.setOnDatabaseRowReadListener(new OnDatabaseRowReadListener<Item>() {
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
