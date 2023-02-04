package com.mihtan.kiwi.api.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface ParameterSetter {

    void setParameter(PreparedStatement statement, int index) throws SQLException;

}
