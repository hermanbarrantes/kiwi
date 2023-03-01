package com.mihtan.kiwi.api;

import java.sql.SQLException;

/**
 *
 * @author herman
 */
public class KiwiException extends RuntimeException {

    public KiwiException(String message) {
        super(message);
    }

    public KiwiException(String message, SQLException cause) {
        super(message, cause);
    }

    public KiwiException(SQLException cause) {
        super(cause);
    }

}
