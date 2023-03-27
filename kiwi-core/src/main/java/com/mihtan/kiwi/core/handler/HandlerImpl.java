package com.mihtan.kiwi.core.handler;

import com.mihtan.kiwi.api.handler.Handler;
import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.Update;
import com.mihtan.kiwi.core.statement.CallImpl;
import com.mihtan.kiwi.core.statement.QueryImpl;
import com.mihtan.kiwi.core.statement.UpdateImpl;
import java.sql.Connection;
import java.sql.SQLException;

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
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface != null && iface.isInstance(connection)
                ? iface.cast(connection)
                : connection.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface != null && iface.isInstance(connection)
                ? true
                : connection.isWrapperFor(iface);
    }

    @Override
    public Query query(String sql) {
        return new QueryImpl(connection, sql);
    }

    @Override
    public Update update(String sql) {
        return new UpdateImpl(connection, sql);
    }

    @Override
    public Call call(String sql) {
        return new CallImpl(connection, sql);
    }

}
