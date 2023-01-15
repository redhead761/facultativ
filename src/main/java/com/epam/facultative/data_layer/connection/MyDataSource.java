package com.epam.facultative.data_layer.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

import static com.epam.facultative.data_layer.connection.ConnectionConstants.*;

public class MyDataSource {

    private static HikariDataSource ds;

    private MyDataSource() {
    }

    public static DataSource getDataSource(Properties properties) {
        if (ds == null) {
            HikariConfig config = getHikariConfig(properties);
            ds = new HikariDataSource(config);
        }
        return ds;
    }

    private static HikariConfig getHikariConfig(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(DRIVER_PROPERTY));

        config.addDataSourceProperty("cachePrepStmts", properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty(CACHE_SQL_LIMIT));
        return config;
    }

}
