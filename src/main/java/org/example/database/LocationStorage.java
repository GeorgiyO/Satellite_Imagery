package org.example.database;

import org.example.domain.location.Location;

import java.util.List;

public interface LocationStorage<Type extends Location> {
    Type get(int id);
    Type get(String name);
    List<Type> getList(int parentId);
    void add(Type location, byte[] image);
    void update(Type location, byte[] image);
    void delete(int id);
}
