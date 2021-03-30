package org.example.database;

import org.example.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@SuppressWarnings("ALL")
public abstract class LocationStorageMySQL<Type extends Location> implements LocationStorage<Type> {

    @Autowired
    protected JdbcTemplate jdbc;

    protected final String table;
    protected final RowMapper<Type> mapper;

    public LocationStorageMySQL(String table, RowMapper<Type> mapper) {
        this.table = table;
        this.mapper = mapper;
    }


    @Override
    public Type get(int id) {
        return jdbc.query(
                "call " + table + "_get_by_id(?)",
                mapper,
                id
        ).get(0);
    }

    @Override
    public Type get(String name) {
        return jdbc.query(
                "call " + table + "_get_by_name(?)",
                mapper,
                name
        ).get(0);
    }

    @Override
    public List<Type> getList(int parentId) {
        return jdbc.query(
                "call " + table + "_get_list(?)",
                mapper,
                parentId
        );
    }

    @Override
    public abstract void add(Type location, byte[] image);

    @Override
    public abstract void update(Type location, byte[] image);

    @Override
    public void delete(int id) {
        jdbc.update(
                "call " + table + "_delete(?)",
                id
        );
    }
}
