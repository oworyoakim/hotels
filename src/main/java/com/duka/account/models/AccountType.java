package com.duka.account.models;

public enum AccountType {
    INDIVIDUAL("Individual"),
    BUSINESS("Business");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
