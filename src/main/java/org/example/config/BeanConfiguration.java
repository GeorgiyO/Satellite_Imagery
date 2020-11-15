package org.example.config;

import org.example.database.country.CountryStorage;
import org.example.database.country.CountryStorageImp;
import org.example.database.image.ImageStorage;
import org.example.database.image.ImageStorageImp;
import org.example.database.user.MySQLUserStorage;
import org.example.database.user.UserStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    CountryStorage countryStorage() {
        return new CountryStorageImp();
    }

    @Bean
    ImageStorage imageStorage() {
        return new ImageStorageImp();
    }
}
