package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.NameParameterSetter;
import com.mihtan.kiwi.api.statement.StatementException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class CallImpl extends BaseStatement<Call> implements Call {

    private final Connection connection;
    private final String sql;

    public CallImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    @Override
    public Call setParamenter(NameParameterSetter parameterSetter) {
        return this;
    }

    @Override
    public void execute() {
        try ( var cs = connection.prepareCall(sql)) {
            applyParameters(cs);//TODO check
            cs.executeUpdate();
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

}
