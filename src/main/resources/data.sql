-- -- users 테이블 생성
-- CREATE TABLE user (
--                        USER_ID INT PRIMARY KEY AUTO_INCREMENT,
--                        EMAIL VARCHAR(255) NOT NULL,
--                        PASSWORD VARCHAR(255) NOT NULL
-- );
--
-- -- AUTHORITY 테이블 생성
-- CREATE TABLE AUTHORITY (
--                            AUTHORITY_ID INT PRIMARY KEY AUTO_INCREMENT,
--                            AUTHORITY_NAME VARCHAR(255) NOT NULL
-- );
--


-- users 테이블에 데이터 삽입
insert into user (EMAIL, PASSWORD)
values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi');

-- AUTHORITY 테이블에 데이터 삽입
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

-- -- USER_AUTHORITY 테이블에 데이터 삽입
-- insert into USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (LAST_INSERT_ID(), 'ROLE_USER');
-- insert into USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (LAST_INSERT_ID(), 'ROLE_ADMIN');
