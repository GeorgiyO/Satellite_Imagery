package org.example.config;

import com.google.gson.Gson;
import org.example.controller.image.form.AddingForm;
import org.example.controller.image.form.UpdatingForm;
import org.example.database.administrator.BackupHandler;
import org.example.database.administrator.WindowsMySQLBackupHandler;
import org.example.database.attraction.AttractionMapper;
import org.example.database.attraction.AttractionStorage;
import org.example.database.attraction.AttractionStorageImp;
import org.example.database.city.CityMapper;
import org.example.database.city.CityStorage;
import org.example.database.city.CityStorageImp;
import org.example.database.country.CountryMapper;
import org.example.database.country.CountryStorage;
import org.example.database.country.CountryStorageImp;
import org.example.database.image.ImageMapper;
import org.example.database.image.ImageStorage;
import org.example.database.image.ImageStorageImp;
import org.example.database.location.LocationMapper;
import org.example.database.location.LocationStorage;
import org.example.database.location.LocationStorageImp;
import org.example.database.region.RegionMapper;
import org.example.database.region.RegionStorage;
import org.example.database.region.RegionStorageImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class BeanConfiguration {
    @Bean
    ImageStorage imageStorage() {
        return new ImageStorageImp();
    }
    @Bean
    ImageMapper imageMapper() {
        return new ImageMapper();
    }

    @Bean
    CountryStorage countryStorage() {
        return new CountryStorageImp();
    }
    @Bean
    CountryMapper countryMapper() {
        return new CountryMapper();
    }

    @Bean
    RegionStorage regionStorage() {
        return new RegionStorageImp();
    }
    @Bean
    RegionMapper regionMapper() {
        return new RegionMapper();
    }

    @Bean
    CityStorage cityStorage() {
        return new CityStorageImp();
    }
    @Bean
    CityMapper cityMapper() {
        return new CityMapper();
    }

    @Bean
    AttractionStorage attractionStorage() {
        return new AttractionStorageImp();
    }
    @Bean
    AttractionMapper attractionMapper() {
        return new AttractionMapper();
    }

    @Bean
    LocationStorage locationStorage() {
        return new LocationStorageImp();
    }
    @Bean
    LocationMapper locationMapper() {
        return new LocationMapper();
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
