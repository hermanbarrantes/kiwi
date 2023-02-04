package com.mihtan.kiwi.api.transaction;

/**
 *
 * @author herman
 */
public interface TransactionManager {

    void begin();

    void commit();

    void rollback();
}
