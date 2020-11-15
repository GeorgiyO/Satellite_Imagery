package org.example.domain.location;

public enum LocationType {
    COUNTRY,
    REGION,
    CITY,
    ATTRACTION;

    public static LocationType fromString(String str) {
        switch (str.toUpperCase()) {
            case "COUNTRY": return COUNTRY;
            case "REGION": return REGION;
            case "CITY": return CITY;
            case "ATTRACTION": return ATTRACTION;
            default: throw new IllegalStateException("Unexpected value: " + str);
        }
    }
}
