package org.example.database.image;

import org.example.domain.Image;
import org.example.domain.location.LocationType;

public interface ImageStorage {
    Image get(LocationType locationType, int id);
    void add(Image image);
    void update(Image image);
    void delete(Image image);
}
