package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import com.mihtan.kiwi.api.result.RowIterableCallback;
import com.mihtan.kiwi.api.result.RowIterableConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

/**
 *
 * @author herman
 */
public interface Query extends Statement<Query> {

    <T, R> R call(RowMapper<T> rowMapper, RowIterableCallback<T, R> callback);

    default <T> void run(RowMapper<T> rowMapper, RowIterableConsumer<T> consumer) {
        call(rowMapper, consumer.asCallback());
    }

    default <T> Optional<T> one(RowMapper<T> rowMapper) {
        return call(rowMapper, it -> {
            var iterator = it.iterator();
            if (iterator.hasNext()) {
                T result = iterator.next();
                if (iterator.hasNext()) {
                    throw new StatementException("Too many results found");
                }
                return Optional.ofNullable(result);
            } else {
                throw new StatementException("Result not found");
            }
        });
    }

    default <T> Optional<T> first(RowMapper<T> rowMapper) {
        return call(rowMapper, it -> {
            var iterator = it.iterator();
            return iterator.hasNext()
                    ? Optional.ofNullable(iterator.next())
                    : Optional.empty();
        });
    }

    default <T> List<T> list(RowMapper<T> rowMapper) {
        return call(rowMapper, it -> {
            List<T> list = new ArrayList<>();
            it.forEach(list::add);
            return list;
        });
    }

    default <T, A, R> R collect(RowMapper<T> rowMapper, Collector<? super T, A, R> collector) {
        return call(rowMapper, it -> it.stream().collect(collector));
    }
}
