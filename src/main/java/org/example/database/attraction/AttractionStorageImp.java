package org.example.database.attraction;

import org.example.domain.location.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class AttractionStorageImp implements AttractionStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Override
    public Attraction get(int id) {
        Attraction attraction = null;

        String sqlQuery = "SELECT * FROM Attraction WHERE id = ?";
        attraction = jdbcTemplate.query(sqlQuery, context.getBean(AttractionMapper.class), id).get(0);

        return attraction;
    }

    @Override
    public Attraction get(String name) {
        Attraction attraction = null;

        String sqlQuery = "SELECT * FROM Attraction WHERE name = ?";
        attraction = jdbcTemplate.query(sqlQuery, context.getBean(AttractionMapper.class), name).get(0);

        return attraction;
    }

    @Override
    public void add(Attraction attraction) {
        String sqlQuery = "INSERT INTO Attraction (name, description, city_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity().getId());
    }

    @Override
    public void update(Attraction attraction) {
        String sqlQuery = "UPDATE Attraction SET name = ?, description = ?, city_id = ?";
        jdbcTemplate.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity().getId());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM Attraction WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
