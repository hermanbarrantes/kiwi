package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.statement.StatementException;
import com.mihtan.kiwi.api.statement.Update;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herman
 */
public class UpdateImpl extends AbstractStatement<Update> implements Update {

    @FunctionalInterface
    private interface PreparedStatementStrategy {

        PreparedStatement apply(Connection connection) throws SQLException;
    }

    private final Connection connection;
    private final String sql;
    private PreparedStatementStrategy strategy;

    public UpdateImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
        this.strategy = con -> con.prepareStatement(sql, RETURN_GENERATED_KEYS);
    }

    @Override
    public int execute() {
        try ( var ps = connection.prepareStatement(sql)) {
            applyParameters(ps);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

    @Override
    public Update mapKeys(String[] columnNames) {
        strategy = con -> con.prepareStatement(sql, columnNames);
        return this;
    }

    @Override
    public Update mapKeys(int[] columnIndexes) {
        strategy = con -> con.prepareStatement(sql, columnIndexes);
        return this;
    }

    @Override
    public <T> List<T> executeAndGetKeys(RowMapper<T> rowMapper) {
        try ( var ps = strategy.apply(connection)) {
            applyParameters(ps);
            int rowCount = ps.executeUpdate();
            if (rowCount < 1) {
                throw new StatementException("No keys generated");
            }
            try ( var rs = ps.getGeneratedKeys()) {
                List<T> keys = new ArrayList<>();
                while (rs.next()) {
                    keys.add(rowMapper.map(rs));
                }
                if (keys.isEmpty()) {
                    throw new StatementException("No keys generated");
                } else {
                    return keys;
                }
            }
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }
}
