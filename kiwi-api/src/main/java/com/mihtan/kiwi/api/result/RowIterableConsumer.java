package com.mihtan.kiwi.api.result;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface RowIterableConsumer<T> {

    void run(RowIterable<T> rowIterable);

    default RowIterableCallback<T, Void> asCallback() {
        return rowIterable -> {
            run(rowIterable);
            return null;
        };
    }
}
