package com.mihtan.kiwi.api;

/**
 *
 * @author herman
 */
public class KiwiException extends RuntimeException {

    public KiwiException(String message) {
        super(message);
    }

    public KiwiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiwiException(Throwable cause) {
        super(cause);
    }

}
