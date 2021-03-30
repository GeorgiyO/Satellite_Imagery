package org.example.database.attraction;

import org.example.database.LocationStorageMySQL;
import org.example.domain.location.Attraction;
import org.springframework.jdbc.core.RowMapper;

public class AttractionStorageMySQL extends LocationStorageMySQL<Attraction> implements AttractionStorage {

    public AttractionStorageMySQL(String table, RowMapper<Attraction> mapper) {
        super(table, mapper);
    }

    @Override
    public void add(Attraction location, byte[] image) {
        jdbc.update(
                "call attraction_add(?, ?, ?, ?)",
                location.getName(),
                location.getDescription(),
                image,
                location.getCity().getId()
        );
    }

    @Override
    public void update(Attraction attraction, byte[] image) {
        jdbc.update(
                "call attraction_update(?, ?, ?, ?, ?, ?)",
                attraction.getName(),
                attraction.getDescription(),
                image,
                attraction.getLocationId(),
                attraction.getCity().getId(),
                attraction.getId()
        );
    }
}
