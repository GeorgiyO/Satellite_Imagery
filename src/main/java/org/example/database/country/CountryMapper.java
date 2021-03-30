package org.example.database.country;

import org.example.database.LocationMapper;
import org.example.domain.location.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet rs, int i) throws SQLException {
        Country country = new Country();
        LocationMapper.setMainFields(rs, country);
        return country;
    }
}
