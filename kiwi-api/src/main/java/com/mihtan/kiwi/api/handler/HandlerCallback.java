package com.mihtan.kiwi.api.handler;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface HandlerCallback<T> {

    T call(Handler handler);
}
