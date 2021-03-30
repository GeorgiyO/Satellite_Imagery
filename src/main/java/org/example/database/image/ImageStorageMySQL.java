package org.example.database.image;

import org.example.domain.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImageStorageMySQL implements ImageStorage {

    @Autowired
    private JdbcTemplate jdbc;

    private final ImageMapper mapper;

    public ImageStorageMySQL(ImageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Image get(int id) {
        System.out.println(id);
        return jdbc.query(
                "call image_get(?)",
                mapper,
                id
        ).get(0);
    }

    @Override
    public void add(Image image) {
        jdbc.update(
                "call image_add(?, ?)",
                image.getLocationId(),
                image.getData()
        );
    }

    @Override
    public void update(Image image) {
        jdbc.update(
                "call image_update(?, ?)",
                image.getLocationId(),
                image.getData()
        );
    }

    @Override
    public void delete(int id) {
        jdbc.update(
                "call image_delete(?)",
                id
        );
    }
}
