package org.example.database.city;

import org.example.database.LocationStorageMySQL;
import org.example.domain.location.City;
import org.springframework.jdbc.core.RowMapper;

public class CityStorageMySQL extends LocationStorageMySQL<City> implements CityStorage {

    public CityStorageMySQL(String table, RowMapper<City> mapper) {
        super(table, mapper);
    }

    @Override
    public void add(City city, byte[] image) {
        jdbc.update(
                "call city_add(?, ?, ?, ?)",
                city.getName(),
                city.getDescription(),
                image,
                city.getRegion().getId()
        );
    }

    @Override
    public void update(City city, byte[] image) {
        jdbc.update(
                "call city_update(?, ?, ?, ?, ?, ?)",
                city.getName(),
                city.getDescription(),
                image,
                city.getLocationId(),
                city.getRegion().getId(),
                city.getId()
        );
    }
}
