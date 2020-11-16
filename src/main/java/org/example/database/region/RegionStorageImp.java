package org.example.database.region;

import org.example.database.country.CountryStorage;
import org.example.domain.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegionStorageImp implements RegionStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Override
    public Region get(int id) {
        Region region = null;

        String sqlQuery = "SELECT * FROM Region WHERE id = ?";
        region = jdbcTemplate.query(sqlQuery, context.getBean(RegionMapper.class), id).get(0);

        return region;
    }

    @Override
    public Region get(String name) {
        Region region = null;

        String sqlQuery = "SELECT * FROM Region WHERE name = ?";
        region = jdbcTemplate.query(sqlQuery, context.getBean(RegionMapper.class), name).get(0);

        return region;
    }

    @Override
    public void add(Region region) {
        String sqlQuery = "INSERT INTO Region (name, description, country_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, region.getName(), region.getDescription(), region.getCountry().getId());
    }

    @Override
    public void update(Region region) {
        String sqlQuery = "UPDATE Region SET name = ?, description = ?, country_id = ?";
        jdbcTemplate.update(sqlQuery, region.getName(), region.getDescription(), region.getCountry().getId());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM Region WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
