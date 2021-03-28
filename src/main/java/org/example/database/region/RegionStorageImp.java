package org.example.database.region;

import org.example.database.city.CityStorage;
import org.example.database.image.ImageStorage;
import org.example.database.location.LocationStorage;
import org.example.domain.Image;
import org.example.domain.location.LocationType;
import org.example.domain.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class RegionStorageImp implements RegionStorage {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private LocationStorage locationStorage;
    @Autowired
    private CityStorage cityStorage;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public Region get(int id) {
        var region = jdbc.query(
                "select * from region where id = ?",
                new RegionMapper(),
                id
        ).get(0);

        var location = locationStorage.get(region.getLocationId());
        location.appendTo(region);

        return region;
    }

    @Override
    public Region get(String name) {
        var location = locationStorage.get(name);
        var region = jdbc.query(
                "select * from region where location_id = ?",
                new RegionMapper(),
                location.getLocationId()
        ).get(0);

        location.appendTo(region);
        return region;
    }

    @Override
    public List<Region> getList(int parentId) {
        var regionList = jdbc.query(
                "select * from region where country_id = ?",
                new RegionMapper(),
                parentId
        );

        for (var region : regionList) {
            var location = locationStorage.get(region.getLocationId());
            location.appendTo(region);
        }

        return regionList;
    }

    @Override
    public void add(Region region) {
        var id = locationStorage.add(region);
        jdbc.update(
                "insert into region (location_id, country_id) values (?, ?)",
                id,
                region.getCountry().getId()
        );
    }

    @Override
    public void update(Region region) {
        locationStorage.update(region);
        jdbc.update(
                "update region set country_id = ? where id = ?",
                region.getCountry().getId(),
                region.getId()
        );
    }

    @Override
    public void delete(int id) {
        cityStorage.deleteByParentId(id);

        Image image = new Image();
        image.setLocationId(id);
        imageStorage.delete(image);

        jdbc.update(
                "delete from region where id = ?"
        );
    }

    @Override
    public void deleteByParentId(int parentId) {
        var idList = jdbc.query(
                "select id from region where country_id = ?",
                (rs, i) -> rs.getInt("id"),
                parentId
        );
        for (var id : idList) {
            delete(id);
        }
    }
}
