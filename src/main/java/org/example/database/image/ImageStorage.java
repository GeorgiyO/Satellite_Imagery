package org.example.database.image;

import org.example.domain.Image;

public interface ImageStorage {
    Image get(int id);
    void add(Image image);
    void update(Image image);
    void delete(int id);
}
