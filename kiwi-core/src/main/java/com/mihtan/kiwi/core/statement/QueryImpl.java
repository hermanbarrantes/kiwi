package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.result.RowIterableCallback;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.StatementException;
import com.mihtan.kiwi.core.result.RowIterableImpl;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class QueryImpl extends AbstractStatement<Query> implements Query {

    private final Connection connection;
    private final String sql;

    public QueryImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    @Override
    public <T, R> R call(RowMapper<T> rowMapper, RowIterableCallback<T, R> callback) {
        try ( var ps = connection.prepareStatement(sql)) {
            applyParameters(ps);
            try ( var rs = ps.executeQuery()) {
                return callback.call(new RowIterableImpl<>(rs, rowMapper));
            }
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

}
