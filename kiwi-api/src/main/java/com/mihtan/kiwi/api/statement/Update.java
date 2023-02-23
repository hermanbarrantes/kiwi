package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import java.util.List;

/**
 *
 * @author herman
 */
public interface Update extends Statement<Update> {

    int execute();

    Update mapKeys(String... columnNames);

    Update mapKeys(int... columnIndexes);

    <T> List<T> executeAndGetKeys(RowMapper<T> rowMapper);

    default <T> List<Long> executeAndGetKeys() {
        return executeAndGetKeys(rs -> rs.getLong(1));
    }

    default <T> T executeAndGetKey(RowMapper<T> rowMapper) {
        return executeAndGetKeys(rowMapper).get(0);
    }

    default Long executeAndGetKey() {
        return executeAndGetKey(rs -> rs.getLong(1));
    }
}
