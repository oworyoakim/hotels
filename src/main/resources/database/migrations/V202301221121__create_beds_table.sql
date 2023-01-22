CREATE TABLE beds
(
    id        bigint NOT NULL AUTO_INCREMENT,
    bedType   varchar(255),
    capacity  int,
    createdAt timestamp,
    PRIMARY KEY (id)
);
