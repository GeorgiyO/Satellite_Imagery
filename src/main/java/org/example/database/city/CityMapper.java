package org.example.database.city;

import org.example.database.country.CountryStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.location.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements RowMapper<City> {

    @Autowired
    private RegionStorage regionStorage;

    @Override
    public City mapRow(ResultSet rs, int i) throws SQLException {
        City city = new City();

        city.setId(rs.getInt("id"));
        city.setName(rs.getString("name"));
        city.setDescription(rs.getString("description"));

        city.setRegion(regionStorage.get(rs.getInt("region_id")));

        return city;
    }
}
