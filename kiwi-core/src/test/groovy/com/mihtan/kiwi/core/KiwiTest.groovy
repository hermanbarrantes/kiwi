package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.mapper.RowMapper
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet
import java.sql.Statement
import javax.sql.DataSource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

/**
 *
 * @author herman
 */
@Testcontainers
class KiwiTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(SpockTestImages.POSTGRES_TEST_IMAGE)
    .withDatabaseName("kiwi")
    .withUsername("username")
    .withPassword("secret")
    .withInitScript("db/init.sql")
    
    static DataSource ds
    
    def setupSpec() {
        HikariConfig hikariConfig = new HikariConfig()
        hikariConfig.setDriverClassName(postgreSQLContainer.driverClassName)
        hikariConfig.setJdbcUrl(postgreSQLContainer.jdbcUrl)
        hikariConfig.setUsername(postgreSQLContainer.username)
        hikariConfig.setPassword(postgreSQLContainer.password)
        ds = new HikariDataSource(hikariConfig)
    }
    
    def "list all elements of a table"() {
        given: "a Kiwi instance"
        Kiwi kiwi = Kiwi.create(ds)

        when: "querying the database"
        List<Book> books = kiwi.call(handler -> 
            handler
            .createQuery("SELECT * FROM book")
            .list(new BookMapper())
        )

        then: "result is returned"
        !books.isEmpty()
        books.size() == 3
    }
    
    def "list some elements of a table"() {
        given: "a Kiwi instance"
        Kiwi kiwi = Kiwi.create(ds)

        when: "querying the database"
        List<Book> books = kiwi.call(handler -> 
            handler
            .createQuery("SELECT * FROM book WHERE active = ?")
            .setBoolean(true)
            .list(new BookMapper())
        )

        then: "result is returned"
        !books.isEmpty()
        books.size() == 2
    }
    
    def cleanupSpec() {
        ds?.close()
    }

}

