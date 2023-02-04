package com.mihtan.kiwi.core.transaction;

import com.mihtan.kiwi.api.transaction.TransactionException;
import com.mihtan.kiwi.api.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class LocalTransactionManager implements TransactionManager {

    private final Connection connection;
    private boolean inTransaction;
    private boolean initialAutocommit;

    public LocalTransactionManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void begin() {
        try {
            if (!inTransaction) {
                initialAutocommit = connection.getAutoCommit();
                connection.setAutoCommit(false);
                inTransaction = true;
            }
        } catch (SQLException ex) {
            throw new TransactionException("Failed to start transaction", ex);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new TransactionException("Failed to commit transaction", ex);
        } finally {
            restoreAutoCommitState();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new TransactionException("Failed to rollback transaction", ex);
        } finally {
            restoreAutoCommitState();
        }
    }

    private void restoreAutoCommitState() {
        try {
            if (initialAutocommit != connection.getAutoCommit()) {
                connection.setAutoCommit(initialAutocommit);
            }
            inTransaction = false;
        } catch (SQLException ex) {
            throw new TransactionException(ex);
        }
    }

}
