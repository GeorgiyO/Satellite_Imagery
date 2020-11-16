package org.example.database.city;

import org.example.domain.location.City;

public interface CityStorage {
    City get(int id);
    City get(String name);
    void add(City city);
    void update(City city);
    void delete(int id);
    void deleteByParentId(int parentId);
}
