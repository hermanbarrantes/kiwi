CREATE TABLE books (
    book_id INT AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    created_on TIMESTAMP NOT NULL,
    PRIMARY KEY(book_id),
    UNIQUE(title)
);

INSERT INTO books (title,author,active,created_on) VALUES ('Effective Java', 'Joshua Bloch', true, NOW());
INSERT INTO books (title,author,active,created_on) VALUES ('Clean Code', 'Robert C. Martin', true, NOW());
INSERT INTO books (title,author,active,created_on) VALUES ('Clean Architecture', 'Robert C. Martin', false, NOW());
