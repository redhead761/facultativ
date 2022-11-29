package com.epam.facultative.daos.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static final Logger logger = LoggerFactory.getLogger(DataSource.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;


    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(Constants.URL_PROPERTY));
        config.setUsername(properties.getProperty(Constants.USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(Constants.PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(Constants.DRIVER_PROPERTY));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DataSource.class
                .getClassLoader()
                .getResourceAsStream(Constants.DB_SETTINGS_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
