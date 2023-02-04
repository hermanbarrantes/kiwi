package com.mihtan.kiwi.api.transaction;

import java.sql.Connection;

/**
 *
 * @author herman
 */
public interface TransactionManagerFactory {

    TransactionManager create(Connection connection);
}
