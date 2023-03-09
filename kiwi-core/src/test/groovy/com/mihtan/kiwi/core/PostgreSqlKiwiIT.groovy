package com.mihtan.kiwi.core

import java.sql.Timestamp
import java.time.LocalDateTime
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared

/**
 *
 * @author herman
 */
@Testcontainers
class PostgreSqlKiwiIT extends AbstractKiwiIT {

    @Shared
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.2")
    .withInitScript("db/postgresql.sql")

    JdbcDatabaseContainer getJdbcDatabaseContainer() {
        return postgreSQLContainer;
    }
    
    def "insert one element into the table with map key name"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "one element is inserted"
        def id = kiwi.call(handler -> handler
            .update("INSERT INTO books (title,author,active,created_on) VALUES (?,?,?,?)")
            .setString("TEST WITH MAP KEY NAME")
            .setString("TEST WITH MAP KEY NAME")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .mapKeys("book_id")
            .executeAndGetKey(row -> row.getLong("book_id")));

        then: "the id is returned"
        id != null

        cleanup:
        kiwi.run(handler -> handler
            .update("DELETE FROM books WHERE book_id = ?")
            .setLong(id)
            .execute())
    }
    
    def "insert three elements into the table with map key name"() {
        given: "a Kiwi instance"
        def kiwi = kiwiHelper(ds)

        when: "three elements are inserted"
        def ids = kiwi.call(handler -> handler
            .update("INSERT INTO books (title,author,active,created_on) VALUES (?,?,?,?),(?,?,?,?),(?,?,?,?)")
            .setString("TEST WITH MAP KEY NAME 1")
            .setString("TEST WITH MAP KEY NAME 1")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .setString("TEST WITH MAP KEY NAME 2")
            .setString("TEST WITH MAP KEY NAME 2")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .setString("TEST WITH MAP KEY NAME 3")
            .setString("TEST WITH MAP KEY NAME 3")
            .setBoolean(true)
            .setTimestamp(Timestamp.valueOf(LocalDateTime.now()))
            .mapKeys("book_id")
            .executeAndGetKeys(row -> row.getLong("book_id")));

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
}

