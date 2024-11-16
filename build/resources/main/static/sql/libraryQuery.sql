SHOW DATABASES;
USE LIBRARY;
SHOW TABLES;

/*	DROP TABLE reservation;
	DROP TABLE loan_history;
	DROP TABLE books;
	DROP TABLE users;
*/

SELECT * FROM users;
SELECT * FROM books;
SELECT * FROM loan_history;
SELECT * FROM reservation;

--  DEFAULT CURRENT_TIMESTAMP 하는게 좋을까? 하지 않는게 좋을까?

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
CREATE INDEX idx_loan_history_book_id_borrowed_at_returned ON loan_history (book_id, borrowed_at, is_returned);

CREATE TABLE reservation (
	reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    book_id INT NOT NULL,
	reserved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX idx_reservation_book_id_user_id ON reservation (book_id, user_id);


INSERT INTO books (book_name, writer, publisher, published_at, registered_at, img_path)
VALUES  ('자바 프로그래밍', '홍길동', '한빛출판사', '2023-05-10', NOW(), '/img/book-img/5-test.png'),
		('스프링 입문', '김철수', '길벗출판사', '2022-09-15', NOW(), '/img/book-img/5-test.png'),
        ('파이썬 완벽 가이드', '이영희', '프리렉', '2021-07-22', NOW(), '/img/book-img/5-test.png'),
		('HTML & CSS 디자인', '박지은', '제이펍', '2020-03-30', NOW(), '/img/book-img/5-test.png');


INSERT INTO loan_history (user_id, book_id) VALUES ('56fa47cc-e61b-40c1-a778-6a84a0db481d', 2);
INSERT INTO loan_history (user_id, book_id) VALUES ('b717493d-260b-45f6-baa7-46c81886536d', 3);

INSERT INTO reservation (user_id, book_id) VALUES ('56fa47cc-e61b-40c1-a778-6a84a0db481d', 2);