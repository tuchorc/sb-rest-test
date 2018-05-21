DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY auto_increment,
    username VARCHAR(128) UNIQUE,
    password VARCHAR(256),
    enabled BOOL,
);

DROP TABLE IF EXISTS people;
CREATE TABLE people (
    person_id BIGINT PRIMARY KEY auto_increment,
    name VARCHAR(32),
    username VARCHAR(128) UNIQUE REFERENCES users (username),
    age INT,
);

DROP TABLE IF EXISTS sb_test_item;
create table sb_test_item (
    item_id BIGINT PRIMARY KEY auto_increment,
    description varchar(25) unique not null
);