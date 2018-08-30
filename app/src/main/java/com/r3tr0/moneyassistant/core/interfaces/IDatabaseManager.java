package com.r3tr0.moneyassistant.core.interfaces;

public interface IDatabaseManager<Query> {
    void createDatabase(String databaseName);
    void executeOrder(String sql);
    Query executeQuery(String sql);
    void closeConnection();
}