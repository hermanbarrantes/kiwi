package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.mapper.RowMapper
import java.sql.ResultSet

/**
 *
 * @author herman
 */
class BookMapper implements RowMapper<Book> {

    Book map(ResultSet rs) {
        Book book = new Book()
        book.id = rs.getLong("book_id")
        book.title = rs.getString("title")
        book.author = rs.getString("author")
        book.active = rs.getBoolean("active")
        book.createdOn = rs.getTimestamp("created_on")
        book
    }

}

