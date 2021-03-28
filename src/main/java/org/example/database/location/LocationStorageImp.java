package org.example.database.location;

import org.example.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class LocationStorageImp implements LocationStorage {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Location get(int id) {
        return jdbc.query(
                "select * from location where id = ?",
                new LocationMapper(),
                id
        ).get(0);
    }

    @Override
    public Location get(String name) {
        return jdbc.query(
                "select * from location where name = ?",
                new LocationMapper(),
                name
        ).get(0);
    }

    @Override
    public int add(Location location) {
        jdbc.update(
                "insert into location (name, description) VALUES (?, ?)",
                location.getName(),
                location.getDescription()
        );
        return this.get(location.getName()).getLocationId();
    }

    @Override
    public void update(Location location) {
        jdbc.update(
                "update location set name = ?, description = ? where id = ?",
                location.getName(),
                location.getDescription(),
                location.getLocationId()
        );
    }

    @Override
    public void delete(int id) {
        jdbc.update(
                "delete from location where id = ?",
                id
        );
    }
}
