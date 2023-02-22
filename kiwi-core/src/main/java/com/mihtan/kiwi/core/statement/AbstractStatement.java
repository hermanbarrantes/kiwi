package com.mihtan.kiwi.core.statement;

import com.mihtan.kiwi.api.statement.ParameterSetter;
import com.mihtan.kiwi.api.statement.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herman
 */
public abstract class AbstractStatement<T extends Statement<T>> implements Statement<T> {

    private final List<ParameterSetter> parameters;

    public AbstractStatement() {
        this.parameters = new ArrayList<>();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public T setParamenter(ParameterSetter parameterSetter) {
        parameters.add(parameterSetter);
        return (T) this;
    }

    protected boolean hasParameters() {
        return !parameters.isEmpty();
    }

    protected void applyParameters(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).setParameter(ps, i + 1);
        }
        parameters.clear();
    }
}
