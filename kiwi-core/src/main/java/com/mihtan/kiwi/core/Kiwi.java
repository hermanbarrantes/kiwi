package com.mihtan.kiwi.core;

import com.mihtan.kiwi.api.ConnectionFactory;
import com.mihtan.kiwi.api.KiwiException;
import com.mihtan.kiwi.api.handler.Handler;
import com.mihtan.kiwi.api.handler.HandlerCallback;
import com.mihtan.kiwi.api.handler.HandlerConsumer;
import com.mihtan.kiwi.api.handler.HandlerFactory;
import com.mihtan.kiwi.api.transaction.TransactionManager;
import com.mihtan.kiwi.api.transaction.TransactionManagerFactory;
import com.mihtan.kiwi.core.handler.HandlerImpl;
import com.mihtan.kiwi.core.transaction.LocalTransactionManager;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.util.Objects;
import javax.sql.DataSource;

/**
 *
 * @author herman
 */
public class Kiwi {

    private static final Logger LOG = System.getLogger(Kiwi.class.getName());
    private final ConnectionFactory connectionFactory;
    private final TransactionManagerFactory transactionManagerFactory;
    private final HandlerFactory handleFactory;

    private Kiwi(ConnectionFactory connectionFactory, TransactionManagerFactory transactionManagerFactory, HandlerFactory handleFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "null connectionFactory");
        this.transactionManagerFactory = Objects.requireNonNull(transactionManagerFactory, "null transactionManagerFactory");
        this.handleFactory = Objects.requireNonNull(handleFactory, "null handleFactory");
    }

    public static Kiwi create(ConnectionFactory connectionSupplier) {
        return new Kiwi(connectionSupplier, LocalTransactionManager::new, HandlerImpl::new);
    }

    public static Kiwi create(DataSource dataSource) {
        return create(dataSource::getConnection);
    }
    
    public static KiwiBuilder withConnectionFactory(ConnectionFactory connectionFactory) {
        return new KiwiBuilder(connectionFactory);
    }

    public <T> T call(HandlerCallback<T> callback) {
        try {
            Connection connection = connectionFactory.openConnection();
            TransactionManager transactionManager = transactionManagerFactory.create(connection);
            Handler handler = handleFactory.create(connection);
            try {
                transactionManager.begin();
                T result = callback.call(handler);
                transactionManager.commit();
                return result;
            } catch (Exception ex) {
                transactionManager.rollback();
                throw ex;
            } finally {
                connectionFactory.closeConnection(connection);
            }
        } catch (KiwiException ex) {
            LOG.log(Level.ERROR, () -> "", ex);
            throw ex;
        } catch (Exception ex) {
            LOG.log(Level.ERROR, () -> "", ex);
            throw new KiwiException(ex);
        }
    }

    public void run(HandlerConsumer consumer) {
        call(consumer.asCallback());
    }

    public static class KiwiBuilder {

        private final ConnectionFactory connectionFactory;
        private TransactionManagerFactory transactionManagerFactory = LocalTransactionManager::new;
        private HandlerFactory handleFactory = HandlerImpl::new;

        private KiwiBuilder(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
        }

        public KiwiBuilder withTransactionManagerFactory(TransactionManagerFactory transactionManagerFactory) {
            this.transactionManagerFactory = transactionManagerFactory;
            return this;
        }

        public KiwiBuilder withHandlerFactory(HandlerFactory handlerFactory) {
            this.handleFactory = handlerFactory;
            return this;
        }

        public Kiwi build() {
            return new Kiwi(connectionFactory, transactionManagerFactory, handleFactory);
        }
    }
}
