package org.example.database.city;

import org.example.database.LocationMapper;
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
        LocationMapper.setMainFields(rs, city);
        city.setRegion(regionStorage.get(rs.getInt("region_id")));
        return city;
    }
}
