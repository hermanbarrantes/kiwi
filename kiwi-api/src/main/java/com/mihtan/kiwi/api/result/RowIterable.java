package com.mihtan.kiwi.api.result;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author herman
 */
public interface RowIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
