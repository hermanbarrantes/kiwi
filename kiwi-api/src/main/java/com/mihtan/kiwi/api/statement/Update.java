package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;

/**
 *
 * @author herman
 */
public interface Update extends Statement<Update> {

    int execute();

    <T> T executeWithKey(RowMapper<T> rowMapper);

    default <T> T executeWithKey(String name, Class<T> clazz) {
        return executeWithKey(rs -> rs.getObject(name, clazz));
    }

    default <T> T executeWithKey(int index, Class<T> clazz) {
        return executeWithKey(rs -> rs.getObject(index, clazz));
    }
}
