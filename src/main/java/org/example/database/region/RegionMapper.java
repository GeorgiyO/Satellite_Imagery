package org.example.database.region;

import org.example.database.LocationMapper;
import org.example.database.country.CountryStorage;
import org.example.domain.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionMapper implements RowMapper<Region> {

    @Autowired
    private CountryStorage countryStorage;

    @Override
    public Region mapRow(ResultSet rs, int i) throws SQLException {
        Region region = new Region();
        LocationMapper.setMainFields(rs, region);
        region.setCountry(countryStorage.get(rs.getInt("country_id")));
        return region;
    }
}
