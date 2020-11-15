package org.example.domain.location;

import java.sql.Blob;

public class Attraction extends Location {

    private int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
