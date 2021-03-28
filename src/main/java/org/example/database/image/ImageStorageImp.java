package org.example.database.image;

import org.example.domain.Image;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImageStorageImp implements ImageStorage {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Image get(LocationType locationType, int id) {
        return jdbc.query(
                "select * from image where location_id = ?",
                new ImageMapper(),
                id
        ).get(0);
    }

    @Override
    public void add(Image image) {
        jdbc.update(
                "INSERT INTO image (location_type, location_id, data) VALUES (?, ?, ?)",
                image.getLocationType().toString(),
                image.getLocationId(),
                image.getData()
        );
    }

    @Override
    public void update(Image image) {
        delete(image);
        add(image);
    }

    @Override
    public void delete(Image image) {
        String sqlQuery = "DELETE FROM image WHERE location_type = ? AND location_id = ?";
        jdbc.update(sqlQuery, image.getLocationType().toString(), image.getLocationId());
    }
}
