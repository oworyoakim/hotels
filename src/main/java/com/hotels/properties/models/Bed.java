package com.hotels.properties.models;

import java.time.LocalDateTime;

import com.hotels.properties.enumerations.BedType;

public class Bed {
    Long id;
    BedType bedType;
    Integer capacity;
    LocalDateTime createdAt;
}
