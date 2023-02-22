package com.mihtan.kiwi.core.mapper;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.statement.StatementException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 *
 * @author herman
 */
public class MapMapper implements RowMapper<Map<String, Object>> {

    public enum CaseStrategy {
        NONE(Function.identity()),
        UPPER_CASE(String::toUpperCase),
        LOWER_CASE(String::toLowerCase);

        private final Function<String, String> strategy;

        private CaseStrategy(Function<String, String> strategy) {
            this.strategy = strategy;
        }

        public String apply(String value) {
            return strategy.apply(value);
        }

    }

    private final CaseStrategy caseStrategy;
    private volatile List<String> columnNames;

    public MapMapper() {
        this.caseStrategy = CaseStrategy.NONE;
    }

    public MapMapper(CaseStrategy caseStrategy) {
        this.caseStrategy = caseStrategy;
    }

    public MapMapper(CaseStrategy caseStrategy, Set<String> columnNames) {
        this.caseStrategy = caseStrategy;
        this.columnNames = new ArrayList<>(columnNames);
    }

    public MapMapper(Set<String> columnNames) {
        this.caseStrategy = CaseStrategy.NONE;
        this.columnNames = new ArrayList<>(columnNames);
    }

    @Override
    public Map<String, Object> map(ResultSet resultSet) throws SQLException {
        // See http://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
        List<String> localColumnNames = this.columnNames;
        if (null == localColumnNames) {
            synchronized (this) {
                localColumnNames = this.columnNames;
                if (null == localColumnNames) {
                    this.columnNames = localColumnNames = getColumnNames(resultSet);
                }
            }
        }
        Map<String, Object> row = new LinkedHashMap<>(localColumnNames.size());
        for (int i = 0; i < localColumnNames.size(); i++) {
            row.put(localColumnNames.get(i), resultSet.getObject(i + 1));
        }
        return row;
    }

    private List<String> getColumnNames(ResultSet resultSet) throws SQLException {
        Set<String> columnNamesSet = new LinkedHashSet<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            String columnName = metaData.getColumnName(i + 1);
            String alias = metaData.getColumnLabel(i + 1);
            String name = caseStrategy.apply(alias == null ? columnName : alias);
            boolean added = columnNamesSet.add(name);
            if (!added) {
                throw new StatementException("Column " + name + " appeared twice in this resultset");
            }
        }
        return new ArrayList<>(columnNamesSet);
    }

}
