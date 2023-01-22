CREATE TABLE amenities
(
    id          bigint NOT NULL AUTO_INCREMENT,
    amenityType varchar(255),
    name        varchar(255),
    avatar      varchar(255),
    createdAt timestamp,
    updatedAt timestamp,
    PRIMARY KEY (id)
);
