package com.mihtan.kiwi.api.statement;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface NameParameterSetter {

    void setParameter(CallableStatement statement) throws SQLException;

}
