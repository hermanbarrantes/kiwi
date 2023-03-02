CREATE TABLE books (
    book_id serial PRIMARY KEY,
    title VARCHAR ( 50 ) UNIQUE NOT NULL,
    author VARCHAR ( 50 ) NOT NULL,
    active BOOLEAN NOT NULL,
    created_on TIMESTAMP NOT NULL
);

INSERT INTO books (title,author,active,created_on) VALUES ('Effective Java', 'Joshua Bloch', true, NOW());
INSERT INTO books (title,author,active,created_on) VALUES ('Clean Code', 'Robert C. Martin', true, NOW());
INSERT INTO books (title,author,active,created_on) VALUES ('Clean Architecture', 'Robert C. Martin', false, NOW());
