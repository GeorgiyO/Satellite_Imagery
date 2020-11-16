package org.example.config;

import org.example.database.city.CityMapper;
import org.example.database.city.CityStorage;
import org.example.database.city.CityStorageImp;
import org.example.database.country.CountryMapper;
import org.example.database.country.CountryStorage;
import org.example.database.country.CountryStorageImp;
import org.example.database.image.ImageMapper;
import org.example.database.image.ImageStorage;
import org.example.database.image.ImageStorageImp;
import org.example.database.region.RegionMapper;
import org.example.database.region.RegionStorage;
import org.example.database.region.RegionStorageImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
