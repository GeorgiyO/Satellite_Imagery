package org.example.database.location;

import org.example.domain.location.Location;

public interface LocationStorage {
    Location get(int id);
    Location get(String name);
    int add(Location location);
    void update(Location location);
    void delete(int id);
}
