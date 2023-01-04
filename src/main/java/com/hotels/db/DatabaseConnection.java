package com.hotels.db;

import lombok.AllArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.*;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class DatabaseConnection {
    private final MySqlDataSource dataSource;

    public Jdbi getConnection() {
        return Jdbi.create(dataSource.getDataSource())
                .installPlugin(new SqlObjectPlugin());
    }
}
