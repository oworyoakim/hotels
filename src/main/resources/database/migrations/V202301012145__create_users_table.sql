CREATE TABLE users (
    id bigint NOT NULL AUTO_INCREMENT,
    clientId bigint NOT NULL,
    type varchar(255),
    name varchar(255) DEFAULT NULL,
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    active tinyint(1) default 0,
    createdAt timestamp,
    updatedAt timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (clientId) REFERENCES clients(id) ON DELETE RESTRICT ON UPDATE CASCADE
);