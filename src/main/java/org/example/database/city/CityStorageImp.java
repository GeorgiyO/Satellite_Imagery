package org.example.database.city;

import org.example.domain.location.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class CityStorageImp implements CityStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Override
    public City get(int id) {
        City city = null;

        String sqlQuery = "SELECT * FROM City WHERE id = ?";
        city = jdbcTemplate.query(sqlQuery, context.getBean(CityMapper.class), id).get(0);

        return city;
    }

    @Override
    public City get(String name) {
        City city = null;

        String sqlQuery = "SELECT * FROM City WHERE name = ?";
        city = jdbcTemplate.query(sqlQuery, context.getBean(CityMapper.class), name).get(0);

        return city;
    }

    @Override
    public void add(City city) {
        String sqlQuery = "INSERT INTO City (name, description, region_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, city.getName(), city.getDescription(), city.getRegion().getId());
    }

    @Override
    public void update(City city) {
        String sqlQuery = "UPDATE City SET name = ?, description = ?, region_id = ?";
        jdbcTemplate.update(sqlQuery, city.getName(), city.getDescription(), city.getRegion().getId());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM City WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
