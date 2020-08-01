CREATE table USER
(
    id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    account_id VARCHAR (100),
    name VARCHAR (50),
    token VARCHAR (36),
    gmt_create BIGINT ,
    gmt_modified BIGINT,
    bio VARCHAR(256),
    avatar_url varchar(100)
);