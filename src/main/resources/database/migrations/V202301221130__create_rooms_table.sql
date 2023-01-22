CREATE TABLE rooms
(
    id          bigint         NOT NULL AUTO_INCREMENT,
    clientId    bigint         NOT NULL,
    propertyId  bigint         NOT NULL,
    name        varchar(255),
    roomType    varchar(255),
    description varchar(500)            DEFAULT NULL,
    nightlyRate decimal(15, 2) NOT NULL,
    bathrooms   int            NOT NULL DEFAULT 1,
    published   boolean                 DEFAULT false,
    createdAt   timestamp,
    updatedAt   timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (clientId) REFERENCES clients (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (propertyId) REFERENCES properties (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
