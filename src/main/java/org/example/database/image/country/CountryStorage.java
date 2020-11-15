package org.example.database.image.country;

import org.example.domain.image.Country;

public interface CountryStorage {
    Country get(int id);
    Country get(String name);
    void add(Country country);
    void update(Country country);
    void delete(int id);
}
