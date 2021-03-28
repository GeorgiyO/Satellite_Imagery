package org.example.database.location;

import org.example.domain.location.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
        var loc = new Location();
        loc.setLocationId(rs.getInt("id"));
        loc.setName(rs.getString("name"));
        loc.setDescription(rs.getString("description"));
        return loc;
    }
}
