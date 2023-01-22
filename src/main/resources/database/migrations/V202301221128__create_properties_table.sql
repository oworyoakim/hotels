CREATE TABLE properties
(
    id              bigint         NOT NULL AUTO_INCREMENT,
    clientId        bigint         NOT NULL,
    userId          bigint         NOT NULL,
    name            varchar(255),
    propertyType    varchar(255),
    description     varchar(500)            DEFAULT NULL,
    nightlyRate     decimal(15, 2) NOT NULL,
    bathrooms       int            NOT NULL DEFAULT 1,
    listingCurrency varchar(255)   NOT NULL,
    checkin         time           NOT NULL,
    checkout        time           NOT NULL,
    address         varchar(255)   NOT NULL,
    country         varchar(255)   NOT NULL,
    city            varchar(255)   NOT NULL,
    zip             varchar(255)   NOT NULL,
    published       boolean                 DEFAULT false,
    createdAt       timestamp,
    updatedAt       timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (clientId) REFERENCES clients (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
