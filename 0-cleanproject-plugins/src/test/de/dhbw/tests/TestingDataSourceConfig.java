package de.dhbw.tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Profile("test")
@Configuration
public class TestingDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        System.out.println("test");
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("V1__create_drink_table.sql")
                .addScript("0001_drinks.sql")
                .build();
    }
}