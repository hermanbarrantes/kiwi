package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.mapper.RowMapper
import com.mihtan.kiwi.api.statement.StatementException
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Timestamp
import java.time.LocalDateTime
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
        def kiwi = Kiwi.create(ds)

        when: "querying the database"
        def books = kiwi.call(handler -> handler
            .query("SELECT * FROM books")
            .list(new BookMapper()))

        then: "result is returned"
        !books.isEmpty()
        books.size() == 3
    }
    
    def "list some elements of a table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)
        def active = true

        when: "querying the database"
        def books = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE active = ?")
            .setBoolean(active)
            .list(new BookMapper()))

        then: "result is returned"
        !books.isEmpty()
        books.size() == 2
    }
    
    def "list one element of a table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)
        def id = 1 as long
        def active = true

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ? AND active = ?")
            .setLong(id)
            .setBoolean(active)
            .one(new BookMapper()))

        then: "result is returned"
        book != null
        book.id == id
        book.active == active
    }
    
    def "list one element of a table and throw result no found"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)
        def id = 99 as long
        def active = true

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ? AND active = ?")
            .setLong(id)
            .setBoolean(active)
            .one(new BookMapper()))

        then: "result is returned"
        def ex = thrown(StatementException)
        ex.message == "Result not found"
    }
    
    def "list one element of a table and throw too many results found"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)
        def active = true

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE active = ?")
            .setBoolean(active)
            .one(new BookMapper()))

        then: "result is returned"
        def ex = thrown(StatementException)
        ex.message == "Too many results found"
    }
    
    def "insert one element of a table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)

        when: "querying the database"
        def id = kiwi.call(handler -> handler
            .update("INSERT INTO books (title,author,active,created_on) VALUES (?,?,?,?)")
            .setString("TEST")
            .setString("TEST")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .executeAndGetKey(row -> row.getLong("book_id")));

        then: "result is returned"
        id != null
    }
    
    def "insert three elements of a table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)

        when: "querying the database"
        def ids = kiwi.call(handler -> handler
            .update("INSERT INTO books (title,author,active,created_on) VALUES (?,?,?,?),(?,?,?,?),(?,?,?,?)")
            .setString("TEST 1")
            .setString("TEST 1")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .setString("TEST 2")
            .setString("TEST 2")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .setString("TEST 3")
            .setString("TEST 3")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .mapKeys("book_id")
            .executeAndGetKeys(row -> row.getLong("book_id")));

        then: "result is returned"
        !ids.isEmpty()
        ids.size() == 3
    }

    def cleanupSpec() {
        ds?.close()
    }

}

