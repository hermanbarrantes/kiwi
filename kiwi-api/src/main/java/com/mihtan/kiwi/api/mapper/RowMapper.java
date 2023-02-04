package com.mihtan.kiwi.api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public interface RowMapper<T> {

    T map(ResultSet resultSet) throws SQLException;
}
