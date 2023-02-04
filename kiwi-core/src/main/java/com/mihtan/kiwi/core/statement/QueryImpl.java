package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.StatementException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author herman
 */
public class QueryImpl extends BaseStatement<Query> implements Query {

    private final Connection connection;
    private final String sql;

    public QueryImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    @Override
    public <T> Optional<T> one(RowMapper<T> rowMapper) {
        try ( var ps = connection.prepareStatement(sql)) {
            applyParameters(ps);
            try ( var rs = ps.executeQuery()) {
                if (rs.next()) {
                    T result = rowMapper.map(rs);
                    if (rs.next()) {
                        throw new StatementException("more that one");
                    }
                    return Optional.ofNullable(result);
                } else {
                    return Optional.empty();
                }
            }
        } catch (StatementException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

    @Override
    public <T> Optional<T> first(RowMapper<T> rowMapper) {
        try ( var ps = connection.prepareStatement(sql)) {
            applyParameters(ps);
            try ( var rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.ofNullable(rowMapper.map(rs))
                        : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

    @Override
    public <T> List<T> list(RowMapper<T> rowMapper) {
        List<T> list = new ArrayList<>();
        try ( var ps = connection.prepareStatement(sql)) {
            applyParameters(ps);
            try ( var rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rowMapper.map(rs));
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

}
