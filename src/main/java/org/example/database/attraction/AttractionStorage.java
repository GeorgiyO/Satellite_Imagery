package org.example.database.attraction;

import org.example.domain.location.Attraction;

public interface AttractionStorage {
    Attraction get(int id);
    Attraction get(String name);
    void add(Attraction attraction);
    void update(Attraction attraction);
    void delete(int id);
}
