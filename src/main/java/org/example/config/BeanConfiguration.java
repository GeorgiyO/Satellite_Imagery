package org.example.config;

import org.example.database.country.CountryStorage;
import org.example.database.country.CountryStorageImp;
import org.example.database.user.MySQLUserStorage;
import org.example.database.user.UserStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    UserStorage userStorage() {
        return new MySQLUserStorage();
    }

    @Bean
    CountryStorage countryStorage() {
        return new CountryStorageImp();
    }
}
