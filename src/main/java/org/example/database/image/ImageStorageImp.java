package org.example.database.image;

import org.example.domain.Image;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImageStorageImp implements ImageStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Image get(LocationType locationType, int id) {
        Image image = null;

        String sqlQuery = "SELECT * FROM Image WHERE location_type = ? and location_id = ?";
        image = jdbcTemplate.query(sqlQuery, new ImageMapper(), locationType.toString(), id).get(0);

        return image;
    }

    @Override
    public void add(Image image) {
        String sqlQuery = "INSERT INTO Image (location_type, location_id, data) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, image.getLocationType().toString(), image.getLocationId(), image.getData());
    }

    @Override
    public void update(Image image) {
        delete(image);
        add(image);
    }

    @Override
    public void delete(Image image) {
        String sqlQuery = "DELETE FROM Image WHERE location_type = ? AND location_id = ?";
        jdbcTemplate.update(sqlQuery, image.getLocationType().toString(), image.getLocationId());
    }
}
