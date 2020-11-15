package org.example.domain.location;

import java.sql.Blob;

public class Region extends Location {

    private int countryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
