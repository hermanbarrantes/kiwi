package com.mihtan.kiwi.core

import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared

/**
 *
 * @author herman
 */
@Testcontainers
class MySqlKiwiIT extends AbstractKiwiIT {

    @Shared
    MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7.34")
    .withInitScript("db/mysql.sql")

    JdbcDatabaseContainer getJdbcDatabaseContainer() {
        return mySQLContainer;
    }
}

