package org.example.database.country;

import org.example.domain.location.Country;

import java.util.List;

public interface CountryStorage {
    Country get(int id);
    Country get(String name);
    List<Country> getList();
    void add(Country country);
    void update(Country country);
    void delete(int id);
}
