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

    default <T> T executeAndGetKey(RowMapper<T> rowMapper) {
        return executeAndGetKeys(rowMapper).get(0);
    }
}
