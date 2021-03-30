package org.example.database.attraction;

import org.example.domain.location.Attraction;

import java.util.List;

public interface AttractionStorage {
    Attraction get(int id);
    Attraction get(String name);
    List<Attraction> getList(int parentId);
    void add(Attraction attraction, byte[] image);
    void update(Attraction attraction, byte[] image);
    void delete(int id);
}
