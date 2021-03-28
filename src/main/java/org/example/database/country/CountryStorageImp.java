package org.example.database.country;

import org.example.database.image.ImageStorage;
import org.example.database.location.LocationStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.Country;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CountryStorageImp implements CountryStorage {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private LocationStorage locationStorage;
    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public Country get(int id) {
        var country = jdbc.query(
                "select * from country where id = ?",
                new CountryMapper(),
                id
        ).get(0);

        var location = locationStorage.get(country.getLocationId());
        location.appendTo(country);

        return country;
    }

    @Override
    public Country get(String name) {
        var location = locationStorage.get(name);
        var country = jdbc.query(
                "select * from country where location_id = ?",
                new CountryMapper(),
                location.getLocationId()
        ).get(0);

        location.appendTo(country);
        return country;
    }

    @Override
    public List<Country> getList() {
        var countryList = jdbc.query(
                "select * from country",
                new CountryMapper()
        );

        for (var country : countryList) {
            var location = locationStorage.get(country.getLocationId());
            location.appendTo(country);
        }

        return countryList;
    }

    @Override
    public void add(Country country) {
        var id = locationStorage.add(country);
        jdbc.update(
                "insert into country (location_id) values (?)",
                id
        );
    }

    @Override
    public void update(Country country) {
        locationStorage.update(country);
    }

    @Override
    public void delete(int id) {

        regionStorage.deleteByParentId(id);

        Image image = new Image();
        image.setLocationId(id);
        imageStorage.delete(image);

        jdbc.update(
                "delete from country where id = ?",
                id
        );
    }
}
