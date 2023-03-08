package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.mapper.RowMapper
import com.mihtan.kiwi.api.statement.StatementException
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.sql.DataSource
import org.testcontainers.containers.JdbcDatabaseContainer
import spock.lang.Specification

/**
 *
 * @author herman
 */
abstract class AbstractKiwiIT extends Specification {

    abstract JdbcDatabaseContainer getJdbcDatabaseContainer();

    static DataSource ds

    def setupSpec() {
        def hikariConfig = new HikariConfig()
        hikariConfig.setDriverClassName(jdbcDatabaseContainer.driverClassName)
        hikariConfig.setJdbcUrl(jdbcDatabaseContainer.jdbcUrl)
        hikariConfig.setUsername(jdbcDatabaseContainer.username)
        hikariConfig.setPassword(jdbcDatabaseContainer.password)
        ds = new HikariDataSource(hikariConfig)
    }

    def "list all elements of a table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)

        when: "querying the database"
        def books = kiwi.call(handler -> handler
            .query("SELECT * FROM books")
            .list(new BookMapper()))

        then: "the result is returned"
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

        then: "the result is returned"
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

        then: "the result is returned"
        book != null
        book.id == id
        book.active == active
    }

    def "list one element of a table and throw result not found"() {
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

        then: "the exception is returned"
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

        then: "the exception is returned"
        def ex = thrown(StatementException)
        ex.message == "Too many results found"
    }

    def "insert one element into the table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)

        when: "one element is inserted"
        def id = kiwi.call(handler -> handler
            .update("INSERT INTO books (title,author,active,created_on) VALUES (?,?,?,?)")
            .setString("TEST")
            .setString("TEST")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .executeAndGetKey(row -> row.getLong(1)));

        then: "the id is returned"
        id != null
    }
    
    def "insert three elements into the table"() {
        given: "a Kiwi instance"
        def kiwi = Kiwi.create(ds)

        when: "three elements are inserted"
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
            .executeAndGetKeys(row -> row.getLong(1)));

        then: "the ids are returned"
        !ids.isEmpty()
        ids.size() == 3
    }

    def cleanupSpec() {
        ds?.close()
    }

}

