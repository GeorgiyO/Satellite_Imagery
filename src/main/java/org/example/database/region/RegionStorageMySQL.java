package org.example.database.region;

import org.example.database.LocationStorageMySQL;
import org.example.domain.location.Region;
import org.springframework.jdbc.core.RowMapper;

public class RegionStorageMySQL extends LocationStorageMySQL<Region> implements RegionStorage {

    public RegionStorageMySQL(String table, RowMapper<Region> mapper) {
        super(table, mapper);
    }

    @Override
    public void add(Region region, byte[] image) {
        jdbc.update(
                "call region_add(?, ?, ?, ?)",
                region.getName(),
                region.getDescription(),
                image,
                region.getCountry().getId()
        );
    }

    @Override
    public void update(Region region, byte[] image) {
        jdbc.update(
                "call region_update(?, ?, ?, ?, ?, ?)",
                region.getName(),
                region.getDescription(),
                image,
                region.getLocationId(),
                region.getCountry().getId(),
                region.getId()
        );
    }
}
