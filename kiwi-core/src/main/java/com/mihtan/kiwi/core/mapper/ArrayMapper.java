package com.mihtan.kiwi.core.mapper;

import com.mihtan.kiwi.api.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class ArrayMapper implements RowMapper<Object[]> {

    @Override
    public Object[] map(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Object[] array = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            array[i] = resultSet.getObject(i + 1);
        }
        return array;
    }

}
