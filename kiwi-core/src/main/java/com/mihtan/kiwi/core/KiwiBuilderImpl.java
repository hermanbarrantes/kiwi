package com.mihtan.kiwi.core;

import com.mihtan.kiwi.api.Kiwi;
import com.mihtan.kiwi.api.KiwiBuilder;
import com.mihtan.kiwi.api.connection.ConnectionFactory;
import com.mihtan.kiwi.api.handler.HandlerFactory;
import com.mihtan.kiwi.api.transaction.TransactionManagerFactory;
import com.mihtan.kiwi.core.handler.HandlerImpl;
import com.mihtan.kiwi.core.transaction.LocalTransactionManager;

/**
 *
 * @author herman
 */
public class KiwiBuilderImpl implements KiwiBuilder {

    private ConnectionFactory connectionFactory;
    private TransactionManagerFactory transactionManagerFactory = LocalTransactionManager::new;
    private HandlerFactory handlerFactory = HandlerImpl::new;

    @Override
    public KiwiBuilder withConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        return this;
    }

    @Override
    public KiwiBuilder withTransactionManagerFactory(TransactionManagerFactory transactionManagerFactory) {
        this.transactionManagerFactory = transactionManagerFactory;
        return this;
    }

    @Override
    public KiwiBuilder withHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        return this;
    }

    @Override
    public Kiwi build() {
        return new KiwiImpl(connectionFactory, transactionManagerFactory, handlerFactory);
    }

    /*public static KiwiImpl create(ConnectionFactory connectionSupplier) {
        return new KiwiBuilder(connectionSupplier).build();
    }

    public static KiwiImpl create(DataSource dataSource) {
        return create(dataSource::getConnection);
    }*/
}
