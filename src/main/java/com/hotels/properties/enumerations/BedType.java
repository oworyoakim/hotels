package com.hotels.properties.enumerations;

public enum BedType {
    SINGLE_BED("Single Bed"),
    DOUBLE_BED("Double Bed"),
    TWIN_BED("Twin Bed"),
    QUEEN_BED("Queen Bed"),
    KING_SIZE("King Size"),
    CRIB("Crib"),
    CABIN("Cabin"),
    FUTON("Futon"),
    MURPHY("Murphy"),
    TRUNDLE("Trundle"),
    SOFA("Sofa");
    private final String displayName;

    BedType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
