package com.mihtan.kiwi.api;

import com.mihtan.kiwi.api.connection.ConnectionFactory;
import com.mihtan.kiwi.api.handler.HandlerFactory;
import com.mihtan.kiwi.api.transaction.TransactionManagerFactory;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author herman
 */
public interface KiwiBuilder {

    KiwiBuilder withConnectionFactory(ConnectionFactory connectionFactory);

    default KiwiBuilder withConnectionFactory(DataSource dataSource) {
        return withConnectionFactory(dataSource::getConnection);
    }

    default KiwiBuilder withConnectionFactory(Connection connection) {
        return withConnectionFactory(new ConnectionFactory() {
            @Override
            public Connection openConnection() throws SQLException {
                return connection;
            }

            @Override
            public void closeConnection(Connection connection) throws SQLException {
                //do nothing
            }
        });
    }

    KiwiBuilder withTransactionManagerFactory(TransactionManagerFactory transactionManagerFactory);

    KiwiBuilder withHandlerFactory(HandlerFactory handlerFactory);

    Kiwi build();
}
