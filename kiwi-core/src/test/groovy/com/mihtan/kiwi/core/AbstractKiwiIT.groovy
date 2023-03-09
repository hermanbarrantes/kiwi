package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.Kiwi
import com.mihtan.kiwi.api.KiwiBuilder
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
    
    def kiwiHelper(DataSource ds) {
        KiwiBuilder builder = new KiwiBuilderImpl();
        return builder.withConnectionFactory(ds).build();
    }

    def "list all elements of a table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def books = kiwi.call(handler -> handler
            .query("SELECT * FROM books ORDER BY book_id ASC")
            .list(new BookMapper()))

        then: "the result is returned"
        !books.isEmpty()
        books.size() == 3
        books.id == [1, 2, 3]
        books.title == ["Effective Java", "Clean Code", "Clean Architecture"]
        books.author == ["Joshua Bloch", "Robert C. Martin", "Robert C. Martin"]
        books.active == [true, true, false]
    }

    def "list some elements of a table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def books = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE active = ?")
            .setBoolean(true)
            .list(new BookMapper()))

        then: "the result is returned"
        !books.isEmpty()
        books.size() == 2
        books.active == [true, true]
    }

    def "list one element of a table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ?")
            .setLong(1)
            .one(new BookMapper()))

        then: "the result is returned"
        book != null
        book.id == 1
    }

    def "list one element of a table and throw result not found"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ?")
            .setLong(99)
            .one(new BookMapper()))

        then: "the exception is returned"
        def ex = thrown(StatementException)
        ex.message == "Result not found"
    }

    def "list one element of a table and throw too many results found"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE active = ?")
            .setBoolean(true)
            .one(new BookMapper()))

        then: "the exception is returned"
        def ex = thrown(StatementException)
        ex.message == "Too many results found"
    }

    def "list first element of a table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ?")
            .setLong(1)
            .first(new BookMapper()))

        then: "the result is returned"
        book != null
        book.id == 1
    }

    def "list first element of a table and get null when not found"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE book_id = ?")
            .setLong(99)
            .first(new BookMapper()))

        then: "the result is returned"
        book == null
    }

    def "list first element of a table and get first when too many results found"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "querying the database"
        def book = kiwi.call(handler -> handler
            .query("SELECT * FROM books WHERE active = ? ORDER BY book_id ASC")
            .setBoolean(true)
            .first(new BookMapper()))

        then: "the result is returned"
        book != null
        book.id == 1
    }

    def "insert one element into the table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

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
        
        cleanup:
        kiwi.run(handler -> handler
            .update("DELETE FROM books WHERE book_id = ?")
            .setLong(id)
            .execute())
    }
    
    def "insert three elements into the table"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

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

        cleanup:
        kiwi.run(handler -> handler
            .update("DELETE FROM books WHERE book_id IN (?,?,?)")
            .setLong(ids[0])
            .setLong(ids[1])
            .setLong(ids[2])
            .execute())
    }

    def cleanupSpec() {
        ds?.close()
    }

}

