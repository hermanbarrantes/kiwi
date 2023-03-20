package com.mihtan.kiwi.api.mapper;

import java.sql.SQLException;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface OutputMapper<T> {

    T map(Output output) throws SQLException;
}
