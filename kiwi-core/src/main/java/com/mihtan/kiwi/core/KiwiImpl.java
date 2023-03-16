package com.mihtan.kiwi.core;

import com.mihtan.kiwi.api.Kiwi;
import com.mihtan.kiwi.api.KiwiException;
import com.mihtan.kiwi.api.connection.ConnectionFactory;
import com.mihtan.kiwi.api.handler.Handler;
import com.mihtan.kiwi.api.handler.HandlerCallback;
import com.mihtan.kiwi.api.handler.HandlerFactory;
import com.mihtan.kiwi.api.transaction.TransactionManager;
import com.mihtan.kiwi.api.transaction.TransactionManagerFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author herman
 */
public class KiwiImpl implements Kiwi {

    private final ConnectionFactory connectionFactory;
    private final TransactionManagerFactory transactionManagerFactory;
    private final HandlerFactory handlerFactory;

    public KiwiImpl(ConnectionFactory connectionFactory, TransactionManagerFactory transactionManagerFactory, HandlerFactory handlerFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "null connectionFactory");
        this.transactionManagerFactory = Objects.requireNonNull(transactionManagerFactory, "null transactionManagerFactory");
        this.handlerFactory = Objects.requireNonNull(handlerFactory, "null handleFactory");
    }

    @Override
    public <T> T call(HandlerCallback<T> callback) {
        Connection connection = null;
        try {
            connection = connectionFactory.openConnection();
            TransactionManager transactionManager = transactionManagerFactory.create(connection);
            Handler handler = handlerFactory.create(connection);
            try {
                transactionManager.begin();
                T result = callback.call(handler);
                transactionManager.commit();
                return result;
            } catch (Throwable th) {
                transactionManager.rollback();
                throw th;
            }
        } catch (SQLException ex) {
            throw new KiwiException(ex);
        } finally {
            try {
                connectionFactory.closeConnection(connection);
            } catch (SQLException ex) {
                throw new KiwiException(ex);
            }
        }
    }

}
