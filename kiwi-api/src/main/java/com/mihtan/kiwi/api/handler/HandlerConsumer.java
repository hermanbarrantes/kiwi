package com.mihtan.kiwi.api.handler;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface HandlerConsumer {

    void run(Handler handler);

    default HandlerCallback<Void> asCallback() {
        return handler -> {
            run(handler);
            return null;
        };
    }
}
