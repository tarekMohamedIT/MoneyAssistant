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

import com.r3tr0.moneyassistant.core.exceptions.utils.ModelExistsException;
import com.r3tr0.moneyassistant.core.interfaces.IDatabaseModel;

import java.util.ArrayList;
import java.util.List;

public class MultiModelDatabaseManager extends BaseDatabaseManager{
    protected List<IDatabaseModel> models;

    public MultiModelDatabaseManager(Context context) {
        super(context, "ma_db");
        models = new ArrayList<>();
    }

    public void addNewModel(IDatabaseModel model){
        for (IDatabaseModel databaseModel : models){
            if (databaseModel.getClass().getName().equals(model.getClass().getName())) {
                throw new ModelExistsException();
            }
        }
        models.add(model);
    }

    public IDatabaseModel getModel(int position){
        return models.get(position);
    }

    public IDatabaseModel getModelByClass(Class c){
        for (IDatabaseModel databaseModel : models){
            if (databaseModel.getClass().equals(c)) {
                return databaseModel;
            }
        }

        return null;
    }

    public void removeModel(int position){
        models.remove(position);
    }

    public void clearModels(){
        this.models.clear();
    }

}
