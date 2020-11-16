package org.example.database.region;

import org.example.domain.location.Region;

public interface RegionStorage {
    Region get(int id);
    Region get(String name);
    void add(Region region);
    void update(Region region);
    void delete(int id);
}
