package org.example.database.city;

import org.example.database.attraction.AttractionStorage;
import org.example.database.image.ImageStorage;
import org.example.domain.Image;
import org.example.domain.location.City;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CityStorageImp implements CityStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private AttractionStorage attractionStorage;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public City get(int id) {
        City city = null;

        String sqlQuery = "SELECT * FROM city WHERE id = ?";
        city = jdbcTemplate.query(sqlQuery, context.getBean(CityMapper.class), id).get(0);

        return city;
    }

    @Override
    public City get(String name) {
        City city = null;

        String sqlQuery = "SELECT * FROM city WHERE name = ?";
        city = jdbcTemplate.query(sqlQuery, context.getBean(CityMapper.class), name).get(0);

        return city;
    }

    @Override
    public List<City> getList(int parentId) {
        String sqlQuery = "SELECT * FROM city WHERE region_id = ?";
        return jdbcTemplate.query(sqlQuery, context.getBean(CityMapper.class), parentId);
    }

    @Override
    public void add(City city) {
        String sqlQuery = "INSERT INTO city (name, description, region_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, city.getName(), city.getDescription(), city.getRegion().getId());
    }

    @Override
    public void update(City city) {
        String sqlQuery = "UPDATE city SET name = ?, description = ?, region_id = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, city.getName(), city.getDescription(), city.getRegion().getId(), city.getId());
    }

    @Override
    public void delete(int id) {
        attractionStorage.deleteByParentId(id);

        Image image = new Image();
        image.setLocationType(LocationType.CITY);
        image.setLocationId(id);
        imageStorage.delete(image);

        String sqlQuery = "DELETE FROM city WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void deleteByParentId(int parentId) {
        String sqlQuery = "SELECT id FROM city WHERE region_id = ?";
        List<Integer> idList = jdbcTemplate.query(sqlQuery, (rs, i) -> rs.getInt("id"), parentId);

        for (var id : idList) {
            delete(id);
        }
    }
}
