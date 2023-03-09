package com.mihtan.kiwi.api;

import com.mihtan.kiwi.api.handler.HandlerCallback;
import com.mihtan.kiwi.api.handler.HandlerConsumer;

/**
 *
 * @author herman
 */
public interface Kiwi {

    <T> T call(HandlerCallback<T> callback);

    default void run(HandlerConsumer consumer) {
        call(consumer.asCallback());
    }
}
