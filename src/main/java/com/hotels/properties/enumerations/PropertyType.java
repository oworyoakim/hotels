package com.hotels.properties.enumerations;

public enum PropertyType {
    HOUSE("House"),
    CONDO("Condo"),
    CABIN("Cabin"),
    COTTAGE("Cottage"),
    GUEST_HOUSE("Guest House"),
    APARTMENT("Apartment"),
    VILLA("Villa"),
    BUNGALOW("Bungalow"),
    FARMHOUSE("Farmhouse"),
    TOWNHOUSE("Townhouse"),
    TINY_HOUSE("Tiny House"),
    CAMPSITE("Campsite"),
    LOFT("Loft"),
    HOSTEL("Hostel");
    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
