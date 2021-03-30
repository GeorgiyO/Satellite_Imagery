package org.example.database.attraction;

import org.example.database.LocationMapper;
import org.example.database.city.CityStorage;
import org.example.domain.location.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttractionMapper implements RowMapper<Attraction> {

    @Autowired
    private CityStorage cityStorage;

    @Override
    public Attraction mapRow(ResultSet rs, int i) throws SQLException {
        Attraction attraction = new Attraction();
        LocationMapper.setMainFields(rs, attraction);
        attraction.setCity(cityStorage.get(rs.getInt("city_id")));
        return attraction;
    }
}
