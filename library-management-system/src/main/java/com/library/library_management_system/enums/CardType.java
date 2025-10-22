package com.library.library_management_system.enums;

public enum CardType {
    BASIC("Bronze"),
    PREMIUM("Silver"),
    VIP("VIP");

    private final String displayName;

    CardType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
