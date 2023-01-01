package com.duka.users.models;

public enum UserType {

    BUSINESS_OWNER("BusinessOwner"),
    NORMAL_USER("User");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
