package com.mihtan.kiwi.api;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface ConnectionFactory {

    Connection openConnection() throws SQLException;

    default void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
