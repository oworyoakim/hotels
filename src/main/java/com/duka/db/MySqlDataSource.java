package com.duka.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class MySqlDataSource {
    private DataSource dataSource;

    public MySqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getPropertyValue("quarkus.datasource.jdbc.url"));
        config.setUsername(getPropertyValue("quarkus.datasource.username"));
        config.setPassword(getPropertyValue("quarkus.datasource.password"));
        config.setMaximumPoolSize(Integer.parseInt(getPropertyValue("quarkus.datasource.jdbc.max-size")));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(10000);

        try {
            dataSource = new HikariDataSource(config);
        } catch (Throwable th) {
            System.out.println(th.getMessage());
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    private String getPropertyValue(String propertyName) {
        return ConfigProvider.getConfig().getValue(propertyName, String.class);
    }
}
