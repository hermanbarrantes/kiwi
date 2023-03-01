package com.mihtan.kiwi.api.transaction;

import com.mihtan.kiwi.api.KiwiException;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class TransactionException extends KiwiException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, SQLException cause) {
        super(message, cause);
    }

    public TransactionException(SQLException cause) {
        super(cause);
    }

}
