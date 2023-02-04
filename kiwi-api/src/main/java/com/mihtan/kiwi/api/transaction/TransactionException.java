package com.mihtan.kiwi.api.transaction;

import com.mihtan.kiwi.api.KiwiException;

/**
 *
 * @author herman
 */
public class TransactionException extends KiwiException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

}
