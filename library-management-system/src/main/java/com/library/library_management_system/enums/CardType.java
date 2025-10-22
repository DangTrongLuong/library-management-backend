package com.library.library_management_system.enums;


public enum CardType {
    Bronze, Silver, VIP;

    public static CardType fromString(String value) {
        for (CardType type : CardType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid card type: " + value);
    }
}
