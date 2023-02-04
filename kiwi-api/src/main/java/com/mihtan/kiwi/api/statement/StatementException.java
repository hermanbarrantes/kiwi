package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.KiwiException;

/**
 *
 * @author herman
 */
public class StatementException extends KiwiException {

    public StatementException(String message) {
        super(message);
    }

    public StatementException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatementException(Throwable cause) {
        super(cause);
    }

}
