package com.mihtan.kiwi.core.handler;

import com.mihtan.kiwi.api.handler.Handler;
import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.Update;
import com.mihtan.kiwi.core.statement.CallImpl;
import com.mihtan.kiwi.core.statement.QueryImpl;
import com.mihtan.kiwi.core.statement.UpdateImpl;
import java.sql.Connection;

/**
 *
 * @author herman
 */
public class HandlerImpl implements Handler {

    private final Connection connection;

    public HandlerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Query createQuery(String sql) {
        return new QueryImpl(connection, sql);
    }

    @Override
    public Update createUpdate(String sql) {
        return new UpdateImpl(connection, sql);
    }

    @Override
    public Call createCall(String sql) {
        return new CallImpl(connection, sql);
    }

}
