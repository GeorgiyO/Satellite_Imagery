package org.example.database.region;

import org.example.database.city.CityStorage;
import org.example.database.image.ImageStorage;
import org.example.domain.Image;
import org.example.domain.location.LocationType;
import org.example.domain.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class RegionStorageImp implements RegionStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private CityStorage cityStorage;
    @Autowired
    private ImageStorage imageStorage;

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
    public List<Region> getList(int parentId) {
        String sqlQuery = "SELECT * FROM Region WHERE country_id = ?";
        return jdbcTemplate.query(sqlQuery, context.getBean(RegionMapper.class), parentId);
    }

    @Override
    public void add(Region region) {
        String sqlQuery = "INSERT INTO Region (name, description, country_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, region.getName(), region.getDescription(), region.getCountry().getId());
    }

    @Override
    public void update(Region region) {
        String sqlQuery = "UPDATE Region SET name = ?, description = ?, country_id = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, region.getName(), region.getDescription(), region.getCountry().getId(), region.getId());
    }

    @Override
    public void delete(int id) {

        cityStorage.deleteByParentId(id);

        Image image = new Image();
        image.setLocationType(LocationType.REGION);
        image.setLocationId(id);
        imageStorage.delete(image);

        String sqlQuery = "DELETE FROM Region WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void deleteByParentId(int parentId) {
        String sqlQuery = "SELECT id FROM Region WHERE country_id = ?";
        List<Integer> idList = jdbcTemplate.query(sqlQuery, (rs, i) -> rs.getInt("id"), parentId);

        for (var id : idList) {
            delete(id);
        }
    }
}
