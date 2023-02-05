package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.statement.StatementException;
import com.mihtan.kiwi.api.statement.Update;
import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *
 * @author herman
 */
public class UpdateImpl extends BaseStatement<Update> implements Update {

    private final Connection connection;
    private final String sql;

    public UpdateImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
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
    public <T> T executeWithKey(RowMapper<T> rowMapper) {
        try ( var ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            applyParameters(ps);
            int rowCount = ps.executeUpdate();
            if(rowCount != 1) {
                throw new StatementException("Not only one row was affected");
            }
            try ( var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rowMapper.map(rs);
                } else {
                    throw new StatementException("No keys generated");
                }
            }
        } catch (StatementException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

}
