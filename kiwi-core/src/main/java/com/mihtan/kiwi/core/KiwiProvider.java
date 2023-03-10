package com.mihtan.kiwi.core;

import com.mihtan.kiwi.api.Kiwi;
import com.mihtan.kiwi.api.KiwiBuilder;
import com.mihtan.kiwi.api.connection.ConnectionFactory;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author herman
 */
public class KiwiProvider {

    public static KiwiBuilder builder() {
        return new KiwiBuilderImpl();
    }

    public static Kiwi create(ConnectionFactory connectionFactory) {
        return builder().withConnectionFactory(connectionFactory).build();
    }

    public static Kiwi create(DataSource dataSource) {
        return builder().withConnectionFactory(dataSource).build();
    }

    public static Kiwi create(Connection connection) {
        return builder().withConnectionFactory(connection).build();
    }
}
