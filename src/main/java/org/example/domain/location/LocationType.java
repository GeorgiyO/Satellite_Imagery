package org.example.domain.location;

public enum LocationType {
    COUNTRY,
    REGION,
    CITY,
    ATTRACTION;

    public static LocationType fromString(String str) {
        return switch (str) {
            case "COUNTRY" -> COUNTRY;
            case "REGION" -> REGION;
            case "CITY" -> CITY;
            case "ATTRACTION" -> ATTRACTION;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }
}
