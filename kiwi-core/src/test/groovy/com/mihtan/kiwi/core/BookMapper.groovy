package com.mihtan.kiwi.core

import com.mihtan.kiwi.api.mapper.Row
import com.mihtan.kiwi.api.mapper.RowMapper

/**
 *
 * @author herman
 */
class BookMapper implements RowMapper<Book> {

    Book map(Row row) {
        def book = new Book()
        book.id = row.getLong("book_id")
        book.title = row.getString("title")
        book.author = row.getString("author")
        book.active = row.getBoolean("active")
        book.createdOn = row.getTimestamp("created_on")
        book
    }

}

