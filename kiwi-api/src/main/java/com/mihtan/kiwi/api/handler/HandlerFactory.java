package com.mihtan.kiwi.api.handler;

import java.sql.Connection;

/**
 *
 * @author herman
 */
public interface HandlerFactory {

    Handler create(Connection connection);
}
