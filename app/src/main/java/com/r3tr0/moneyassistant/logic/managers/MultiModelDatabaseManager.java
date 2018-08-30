package com.r3tr0.moneyassistant.logic.managers;

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
