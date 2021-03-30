package org.example.database.country;

import org.example.database.LocationStorageMySQL;
import org.example.domain.location.Country;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class CountryStorageMySQL extends LocationStorageMySQL<Country> implements CountryStorage {

    public CountryStorageMySQL(String table, RowMapper<Country> mapper) {
        super(table, mapper);
    }

    @Override
    public List<Country> getList() {
        return jdbc.query(
                "call country_get_list()",
                mapper
        );
    }

    @Override
    public List<Country> getList(int parentId) {
        throw new RuntimeException("there is no declared method getList(int parentId) for CountryStorage");
    }

    @Override
    public void add(Country country, byte[] image) {
        jdbc.update(
                "call country_add(?, ?, ?)",
                country.getName(),
                country.getDescription(),
                image
        );
    }

    @Override
    public void update(Country country, byte[] image) {
        jdbc.update(
                "call country_update(?, ?, ?, ?, ?)",
                country.getName(),
                country.getDescription(),
                image,
                country.getLocationId(),
                country.getId()
        );
    }
}
