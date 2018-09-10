package com.r3tr0.moneyassistant.core.interfaces;

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

import java.util.List;

/**
 * Used to communicate with the database to grab the items from the database.
 * <p>
 * The main idea of the Database Model is to provide methods for pure-java classes to communicate
 * with the database and to bind that model to a database manager through which the methods
 * are executed and the program can communicate with a database.
 *
 * @param <V> The type of data the should be returned from the database,
 *            ie : {@link com.r3tr0.moneyassistant.core.models.Item}.
 */
public interface IDatabaseModel<V> {

    /**
     * The table creating method
     */
    void createTable();

    /**
     *
     */
    void dropTable();

    /**
     * The new item adding method.
     *
     * @param data The new data item.
     */
    void addNew(V data);

    /**
     *the deleting method by item id
     * @param id The ID of the required item to be deleted.
     */
    void delete(int id);

    /**
     * A method that gets all the items from the table
     * @return A list of items from the table
     */
    List<V> getAllItems();

    /**
     * The item replacing method
     * @param id The ID of the required item
     * @param newData The new item's data
     */
    void replaceItem(int id, V newData);

    /**
     * A method for clearing the table.
     */
    void clearAll();
}
