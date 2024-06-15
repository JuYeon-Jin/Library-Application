SHOW DATABASES;
USE LIBRARY;
SHOW TABLES;
SELECT * FROM USER;
SELECT * FROM BOOK;
SELECT * FROM LOANHISTORY;

CREATE TABLE USER (
    ID CHAR(36) PRIMARY KEY,
    USERNAME VARCHAR(80) NOT NULL,
    PASSWORD VARCHAR(80) NOT NULL,
    ROLE VARCHAR(40) NOT NULL DEFAULT 'ROLE_USER',
    SIGNUP_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE BOOK (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    BOOKNAME VARCHAR(255) NOT NULL,
    STATUS BOOLEAN NOT NULL DEFAULT FALSE,
    REGI_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DELETE_BOOK BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE LOANHISTORY (
	ID INT PRIMARY KEY,
    USER_ID CHAR(36) NOT NULL,
    BOOK_ID INT NOT NULL,
    LOAN_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    RETURN_BOOK BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (USER_ID) REFERENCES USER(ID),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID)
);
