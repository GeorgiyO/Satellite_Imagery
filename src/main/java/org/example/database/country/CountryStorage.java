package org.example.database.country;

import org.example.domain.location.Country;

public interface CountryStorage {
    Country get(int id);
    Country get(String name);
    void add(Country country);
    void update(Country country);
    void delete(int id);
}
