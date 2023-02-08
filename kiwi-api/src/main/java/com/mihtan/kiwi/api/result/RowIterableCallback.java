package com.mihtan.kiwi.api.result;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface RowIterableCallback<T, R> {

    R call(RowIterable<T> rowIterable);

}
