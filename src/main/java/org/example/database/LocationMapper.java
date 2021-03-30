package org.example.database;


import org.example.domain.location.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class LocationMapper {

    public static void setMainFields(ResultSet rs, Location location) throws SQLException {
        location.setId(rs.getInt("id"));
        location.setLocationId(rs.getInt("location_id"));
        location.setName(rs.getString("name"));
        location.setDescription(rs.getString("description"));
    }

}
