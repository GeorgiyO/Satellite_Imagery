package org.example.database.attraction;

import org.example.database.image.ImageStorage;
import org.example.domain.Image;
import org.example.domain.location.Attraction;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AttractionStorageImp implements AttractionStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private ImageStorage imageStorage;

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

        Image image = new Image();
        image.setLocationType(LocationType.ATTRACTION);
        image.setLocationId(id);
        imageStorage.delete(image);

        String sqlQuery = "DELETE FROM Attraction WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void deleteByParentId(int parentId) {
        String sqlQuery = "SELECT id FROM Attraction WHERE city_id = ?";
        List<Integer> idList = jdbcTemplate.query(sqlQuery, (rs, i) -> rs.getInt("id"), parentId);

        for (var id : idList) {
            delete(id);
        }
    }
}
