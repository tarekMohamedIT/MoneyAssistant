package com.r3tr0.moneyassistant.core.database.models;

import com.r3tr0.moneyassistant.core.interfaces.IDatabaseManager;
import com.r3tr0.moneyassistant.core.interfaces.IDatabaseModel;

/**
 * The base of the database model classes.
 * The {@link BaseDatabaseModel} class is essentially a collection of behaviors that is used
 * to communicate with the database.
 *
 * @param <T> the model that should this model operate on,
 *            for example {@link com.r3tr0.moneyassistant.core.models.Item}
 */
public abstract class BaseDatabaseModel<T> implements IDatabaseModel<T> {
    //The database manager that this model should communicate with the database through.
    IDatabaseManager manager;

    /**
     * A constructor to the {@link BaseDatabaseModel} class.
     * @param manager The linked {@link IDatabaseManager} class.
     */
    public BaseDatabaseModel(IDatabaseManager manager){
        this.manager = manager;
    }

    /**
     * A setter for the linked manager.
     * @param manager The new {@link IDatabaseManager} class.
     */
    public void setManager(IDatabaseManager manager) {
        this.manager = manager;
    }

    /**
     * Overriding the finalize method to clear the linked manager.
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        manager = null;
    }
}
