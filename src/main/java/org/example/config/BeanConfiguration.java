package org.example.config;

import com.google.gson.Gson;
import org.example.controller.image.form.AddingForm;
import org.example.controller.image.form.UpdatingForm;
import org.example.database.administrator.BackupHandler;
import org.example.database.administrator.WindowsMySQLBackupHandler;
import org.example.database.attraction.AttractionMapper;
import org.example.database.attraction.AttractionStorage;
import org.example.database.attraction.AttractionStorageMySQL;
import org.example.database.city.CityMapper;
import org.example.database.city.CityStorage;
import org.example.database.city.CityStorageMySQL;
import org.example.database.country.CountryMapper;
import org.example.database.country.CountryStorage;
import org.example.database.country.CountryStorageMySQL;
import org.example.database.image.ImageMapper;
import org.example.database.image.ImageStorage;
import org.example.database.image.ImageStorageMySQL;
import org.example.database.region.RegionMapper;
import org.example.database.region.RegionStorage;
import org.example.database.region.RegionStorageMySQL;
import org.example.database.user.UserMapper;
import org.example.database.user.UserService;
import org.example.database.user.UserStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class BeanConfiguration {

    @Bean
    ImageStorage imageStorage() {
        return new ImageStorageMySQL(imageMapper());
    }

    @Bean
    ImageMapper imageMapper() {
        return new ImageMapper();
    }

    @Bean
    CountryStorage countryStorage() {
        return new CountryStorageMySQL("country", countryMapper());
    }

    @Bean
    CountryMapper countryMapper() {
        return new CountryMapper();
    }

    @Bean
    RegionStorage regionStorage() {
        return new RegionStorageMySQL("region", regionMapper());
    }

    @Bean
    RegionMapper regionMapper() {
        return new RegionMapper();
    }

    @Bean
    CityStorage cityStorage() {
        return new CityStorageMySQL("city", cityMapper());
    }

    @Bean
    CityMapper cityMapper() {
        return new CityMapper();
    }

    @Bean
    AttractionStorage attractionStorage() {
        return new AttractionStorageMySQL("attraction", attractionMapper());
    }

    @Bean
    AttractionMapper attractionMapper() {
        return new AttractionMapper();
    }

    @Bean
    UserStorage userStorage() {
        return new UserService();
    }

    @Bean
    UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    UserService userService() {
        return new UserService();
    }

    @Bean
    BackupHandler backupHandler() {
        return new WindowsMySQLBackupHandler();
    }

    @Bean
    @SessionScope
    AddingForm addingForm() {
        return new AddingForm();
    }

    @Bean
    @SessionScope
    UpdatingForm updatingForm() {
        return new UpdatingForm();
    }

    @Bean
    Gson gson() {
        return new Gson();
    }

}
