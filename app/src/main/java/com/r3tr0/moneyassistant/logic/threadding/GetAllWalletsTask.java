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

import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.List;

public class GetAllWalletsTask extends BaseBackgroundTask {
    private WalletsDatabaseModel model;

    public GetAllWalletsTask(WalletsDatabaseModel databaseModel) {
        super();
        setUseCase(new IUseCase<List<Wallet>>() {
            @Override
            public List<Wallet> processInBackground() {
                return model.getAllItems();
            }
        });
        this.model = databaseModel;
    }
}
