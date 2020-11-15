package org.example.domain.location;

import java.sql.Blob;

public class City extends Location {

    private int regionId;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
