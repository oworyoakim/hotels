CREATE TABLE clients (
    id bigint NOT NULL AUTO_INCREMENT,
    accountType varchar(255),
    subdomain varchar(255) UNIQUE,
    name varchar(255),
    email varchar(255) UNIQUE,
    phone varchar(255),
    address varchar(255),
    country varchar(255),
    city varchar(255),
    zip varchar(255),
    active tinyint(1) default 0,
    createdAt timestamp,
    updatedAt timestamp,
    PRIMARY KEY (id)
);