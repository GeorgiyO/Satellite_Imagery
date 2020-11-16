package org.example.domain.location;

import java.sql.Blob;

public class Attraction extends Location {

    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
