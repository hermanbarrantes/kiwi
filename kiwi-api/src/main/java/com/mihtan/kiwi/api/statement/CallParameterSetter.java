package com.mihtan.kiwi.api.statement;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface CallParameterSetter {

    void setParameter(CallableStatement statement) throws SQLException;

}
