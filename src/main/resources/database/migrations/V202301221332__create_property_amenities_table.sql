CREATE TABLE property_amenities
(
    id         bigint NOT NULL AUTO_INCREMENT,
    propertyId bigint NOT NULL,
    amenityId  bigint NOT NULL,
    createdAt  timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (propertyId) REFERENCES properties (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (amenityId) REFERENCES amenities (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
