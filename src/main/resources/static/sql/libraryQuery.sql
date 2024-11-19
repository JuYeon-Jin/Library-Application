SHOW DATABASES;
USE LIBRARY;
SHOW TABLES;

SELECT * FROM users;
SELECT * FROM books;
SELECT * FROM loan_history;
SELECT * FROM reservation;

CREATE TABLE users (
    user_id CHAR(36) PRIMARY KEY,
    username VARCHAR(40) NOT NULL,
    password VARCHAR(80) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    signed_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE books (
	book_id INT AUTO_INCREMENT PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    writer VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    img_path VARCHAR(50) NOT NULL,
    published_at DATE NOT NULL,
    registered_at TIMESTAMP NOT NULL
);

CREATE TABLE loan_history (
	loan_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    book_id INT NOT NULL,
    borrowed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_returned BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reservation (
	reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    book_id INT NOT NULL,
	reserved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_loan_history_book_id_borrowed_at_returned ON loan_history (book_id, borrowed_at, is_returned);
CREATE INDEX idx_reservation_book_id_user_id ON reservation (book_id, user_id);
