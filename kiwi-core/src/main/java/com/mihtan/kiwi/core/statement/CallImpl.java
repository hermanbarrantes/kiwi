package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.mapper.OutputMapper;
import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.CallParameterSetter;
import com.mihtan.kiwi.api.statement.StatementException;
import com.mihtan.kiwi.core.mapper.OutputImpl;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class CallImpl extends AbstractStatement<Call> implements Call {

    private final Connection connection;
    private final String sql;

    public CallImpl(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    @Override
    public Call setParamenter(CallParameterSetter parameterSetter) {
        return this;
    }

    @Override
    public <T> T invoke(OutputMapper<T> outputMapper) {
        try ( var cs = connection.prepareCall(sql)) {
            applyParameters(cs);//TODO check
            cs.execute();
            return outputMapper.map(new OutputImpl(cs));
        } catch (SQLException ex) {
            throw new StatementException(ex);
        }
    }

}
