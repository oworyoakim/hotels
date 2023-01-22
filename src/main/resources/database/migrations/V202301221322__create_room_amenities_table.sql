CREATE TABLE room_amenities
(
    id        bigint NOT NULL AUTO_INCREMENT,
    roomId    bigint NOT NULL,
    amenityId bigint NOT NULL,
    createdAt timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (roomId) REFERENCES rooms (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (amenityId) REFERENCES amenities (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
