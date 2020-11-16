package org.example.domain.location;

import java.sql.Blob;

public class Region extends Location {

    private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
