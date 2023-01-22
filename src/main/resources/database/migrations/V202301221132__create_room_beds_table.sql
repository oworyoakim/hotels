CREATE TABLE room_beds
(
    id        bigint NOT NULL AUTO_INCREMENT,
    roomId    bigint NOT NULL,
    bedId     bigint NOT NULL,
    quantity  int NOT NULL DEFAULT 1,
    createdAt timestamp,
    updatedAt timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (roomId) REFERENCES rooms (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (bedId) REFERENCES beds (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
