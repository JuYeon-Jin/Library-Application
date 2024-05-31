SHOW DATABASES;
USE LIBRARY;
SHOW TABLES;
SELECT * FROM USER;

CREATE TABLE USER (
    PRIVATE_ID CHAR(36) PRIMARY KEY,
    ID VARCHAR(40) NOT NULL,
    PW VARCHAR(40) NOT NULL,
    GRADE TINYINT DEFAULT 0,
    SIGNUP_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 임의 데이터 삽입
-- INSERT INTO USER (PRIVATE_ID, ID, PW, GRADE) VALUES (UUID(), '관리자', 'admin1', 9);
-- INSERT INTO USER (PRIVATE_ID, ID, PW) VALUES (UUID(), '테스트1', 'test1');
-- INSERT INTO USER (PRIVATE_ID, ID, PW) VALUES (UUID(), '테스트2', 'test2');
-- INSERT INTO USER (PRIVATE_ID, ID, PW) VALUES (UUID(), '테스트3', 'test3');
-- INSERT INTO USER (PRIVATE_ID, ID, PW) VALUES (UUID(), '테스트4', 'test4');
DELETE FROM USER WHERE PRIVATE_ID = '';

SELECT * FROM USER;