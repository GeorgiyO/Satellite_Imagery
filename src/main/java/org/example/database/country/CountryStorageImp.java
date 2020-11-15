package org.example.database.country;

import org.example.domain.location.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CountryStorageImp implements CountryStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Country get(int id) {
        Country country = null;

        String sqlQuery = "SELECT * FROM Country WHERE id = ?";
        country = jdbcTemplate.query(sqlQuery, new CountryMapper(), id).get(0);

        return country;
    }

    @Override
    public Country get(String name) {
        Country country = null;

        String sqlQuery = "SELECT * FROM Country WHERE name = ?";
        country = jdbcTemplate.query(sqlQuery, new CountryMapper(), name).get(0);

        return country;
    }

    @Override
    public void add(Country country) {
        String sqlQuery = "INSERT INTO Country (name, description) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, country.getName(), country.getDescription());
    }

    @Override
    public void update(Country country) {
        String sqlQuery = "UPDATE Country SET name = ?, description = ?";
        jdbcTemplate.update(sqlQuery, country.getName(), country.getDescription());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM Country WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
