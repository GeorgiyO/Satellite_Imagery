package org.example.controller.image.form;

import com.google.gson.JsonObject;
import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.location.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class UpdatingForm {

    private static final Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я, ]+");

    @Autowired
    private CountryStorage countryStorage;
    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private CityStorage cityStorage;
    @Autowired
    private AttractionStorage attractionStorage;

    private LocationType type;
    private String name;
    private String parentName;
    private String newName;
    private String description;

    private JsonObject errors;

    public void setData(LocationType type, String name, String parentName, String newName, String description) {

        errors = new JsonObject(); // пришли новые данные значит ошибки нужно сбросить

        this.type = type;
        this.name = name;
        this.parentName = parentName;
        this.newName = newName;
        this.description = description;
    }

    public boolean isValid() {
        boolean isValid = true;

        if (!fieldIsEmpty(parentName) && !fieldIsMatches(parentName)) {
            errors.addProperty("parentName", "Некорректный ввод");
            isValid = false;
        }
        if (!fieldIsEmpty(newName) && !fieldIsMatches(newName)) {
            errors.addProperty("newName", "Некорректный ввод");
            isValid = false;
        }
        if (!fieldIsEmpty(description) && !fieldIsMatches(description)) {
            errors.addProperty("description", "Некорректный ввод");
            isValid = false;
        }

        return isValid;
    }

    private boolean fieldIsMatches(String field) {
        return pattern.matcher(field).matches();
    }

    private boolean fieldIsEmpty(String field) {
        return field.strip().length() == 0;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public int updateLocation() {
        switch (type) {
            case COUNTRY: return updateCountry();
            case REGION: return updateRegion();
            case CITY: return updateCity();
            case ATTRACTION: return updateAttraction();
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private int updateCountry() {
        Country country = countryStorage.get(name);

        if (!fieldIsEmpty(newName)) country.setName(newName);
        if (!fieldIsEmpty(description)) country.setDescription(description);

        countryStorage.update(country);
        return country.getId();
    }

    private int updateRegion() {
        Region region = regionStorage.get(name);

        if (!fieldIsEmpty(parentName)) region.setCountry(countryStorage.get(parentName));

        if (!fieldIsEmpty(newName)) region.setName(newName);
        if (!fieldIsEmpty(description)) region.setDescription(description);

        regionStorage.update(region);
        return region.getId();
    }

    private int updateCity() {
        City city = cityStorage.get(name);

        if (!fieldIsEmpty(parentName)) city.setRegion(regionStorage.get(parentName));

        if (!fieldIsEmpty(newName)) city.setName(newName);
        if (!fieldIsEmpty(description)) city.setDescription(description);

        cityStorage.update(city);
        return city.getId();
    }

    private int updateAttraction() {
        Attraction attraction = attractionStorage.get(name);

        if (!fieldIsEmpty(parentName)) attraction.setCity(cityStorage.get(parentName));

        if (!fieldIsEmpty(description)) attraction.setDescription(description);
        if (!fieldIsEmpty(newName)) attraction.setName(newName);

        attractionStorage.update(attraction);
        return attraction.getId();
    }
}
