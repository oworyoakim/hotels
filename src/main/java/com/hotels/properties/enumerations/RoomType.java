package com.hotels.properties.enumerations;

public enum RoomType {
    SINGLE_ROOM("Single Room"),
    DOUBLE_ROOM("Double Room"),
    TWIN_ROOM("Twin Room"),
    SUITE("Suite"),
    PRESIDENTIAL_SUITE("Presidential Suite"),
    BRIDAL_SUITE("Bridal Suite"),
    STUDIO("Studio"),
    PENTHOUSE("Penthouse");
    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
