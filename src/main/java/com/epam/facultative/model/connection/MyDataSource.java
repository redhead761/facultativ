package com.epam.facultative.model.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class to configure and obtain HikariDataSource. Use it to connect to database
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class MyDataSource {

    private static HikariDataSource ds;

    private MyDataSource() {
    }

    /**
     * Configures and obtains HikariDataSource.
     *
     * @param properties - all required info to configure datasource
     * @return singleton instance of HikariDataSource
     */
    public static DataSource getDataSource(Properties properties) {
        if (ds == null) {
            HikariConfig config = getHikariConfig(properties);
            ds = new HikariDataSource(config);
        }
        return ds;
    }

    private static HikariConfig getHikariConfig(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty(ConnectionConstants.URL_PROPERTY));
        config.setUsername(properties.getProperty(ConnectionConstants.USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(ConnectionConstants.PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(ConnectionConstants.DRIVER_PROPERTY));

        config.addDataSourceProperty("cachePrepStmts", properties.getProperty(ConnectionConstants.CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty(ConnectionConstants.CACHE_SIZE));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty(ConnectionConstants.CACHE_SQL_LIMIT));
        return config;
    }

}
