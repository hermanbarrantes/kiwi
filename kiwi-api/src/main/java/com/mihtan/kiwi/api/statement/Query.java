package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.result.RowIterableCallback;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author herman
 */
public interface Query extends Statement<Query> {

    <T, R> R iterate(RowMapper<T> rowMapper, RowIterableCallback<T, R> callback);

    default <T> Optional<T> one(RowMapper<T> rowMapper) {
        return iterate(rowMapper, it -> {
            var iterator = it.iterator();
            if (iterator.hasNext()) {
                T result = iterator.next();
                if (iterator.hasNext()) {
                    throw new StatementException("more that one");
                }
                return Optional.ofNullable(result);
            } else {
                return Optional.empty();
            }
        });
    }

    default <T> Optional<T> first(RowMapper<T> rowMapper) {
        return iterate(rowMapper, it -> {
            var iterator = it.iterator();
            return iterator.hasNext()
                    ? Optional.ofNullable(iterator.next())
                    : Optional.empty();
        });
    }

    default <T, A, R> R collect(RowMapper<T> rowMapper, Collector<? super T, A, R> collector) {
        return iterate(rowMapper, it -> it.stream().collect(collector));
    }

    default <T> List<T> list(RowMapper<T> rowMapper) {
        return collect(rowMapper, Collectors.toList());
    }
}
