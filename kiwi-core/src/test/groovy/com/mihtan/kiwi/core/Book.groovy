package com.mihtan.kiwi.core

import groovy.transform.ToString

/**
 *
 * @author herman
 */
@ToString(includeNames=true)
class Book {
    Long id
    String title
    String author
    Boolean active
    Date createdOn
}

