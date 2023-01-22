package com.hotels.properties.enumerations;

public enum AmenityType {
    ROOM_AMENITY("ROOM_AMENITY"),
    PROPERTY_AMENITY("PROPERTY_AMENITY");

    private final String displayName;

    AmenityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
