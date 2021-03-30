package org.example.database.country;

import org.example.database.LocationStorage;
import org.example.domain.location.Country;

import java.util.List;

public interface CountryStorage extends LocationStorage<Country> {
    List<Country> getList();
}
