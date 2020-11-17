package org.example.database.attraction;

import org.example.domain.location.Attraction;

import java.util.List;

public interface AttractionStorage {
    Attraction get(int id);
    Attraction get(String name);
    List<Attraction> getList(int parentId);
    void add(Attraction attraction);
    void update(Attraction attraction);
    void delete(int id);
    void deleteByParentId(int parentId);
}
