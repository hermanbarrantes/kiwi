package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.KiwiException;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class StatementException extends KiwiException {

    public StatementException(String message) {
        super(message);
    }

    public StatementException(String message, SQLException cause) {
        super(message, cause);
    }

    public StatementException(SQLException cause) {
        super(cause);
    }

}
