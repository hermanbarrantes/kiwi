package com.mihtan.kiwi.core.result;

import com.mihtan.kiwi.api.mapper.Row;
import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.result.RowIterable;
import com.mihtan.kiwi.api.statement.StatementException;
import com.mihtan.kiwi.core.mapper.RowImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author herman
 */
public class RowIterableImpl<T> implements RowIterable<T> {

    private final ResultSet resultSet;
    private final RowMapper<T> rowMapper;
    private final Row row;

    public RowIterableImpl(ResultSet resultSet, RowMapper<T> rowMapper) {
        this.resultSet = resultSet;
        this.rowMapper = rowMapper;
        this.row = new RowImpl(resultSet);
    }

    private boolean safeHasNext() {
        try {
            return resultSet.next();
        } catch (SQLException ex) {
            throw new StatementException("No more results", ex);
        }
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            private boolean hasNext = safeHasNext();

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                try {
                    T result = rowMapper.map(row);
                    hasNext = safeHasNext();
                    return result;
                } catch (SQLException ex) {
                    throw new StatementException("An error occurred while mapping", ex);
                }
            }
        };

    }

}
