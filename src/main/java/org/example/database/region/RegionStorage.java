package org.example.database.region;

import org.example.domain.location.Region;

import java.util.List;

public interface RegionStorage {
    Region get(int id);
    Region get(String name);
    List<Region> getList(int parentId);
    void add(Region region);
    void update(Region region);
    void delete(int id);
    void deleteByParentId(int parentId);
}
