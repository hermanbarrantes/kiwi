package com.mihtan.kiwi.api.mapper;

import java.sql.SQLException;

/**
 *
 * @author herman
 */
public interface RowMapper<T> {

    T map(Row row) throws SQLException;
}
