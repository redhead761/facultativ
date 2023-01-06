package com.epam.facultative.data_layer.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyDataSource {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(ConnectionConstants.URL_PROPERTY));
        config.setUsername(properties.getProperty(ConnectionConstants.USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(ConnectionConstants.PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(ConnectionConstants.DRIVER_PROPERTY));

        config.addDataSourceProperty("cachePrepStmts", properties.getProperty(ConnectionConstants.CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty(ConnectionConstants.CACHE_SIZE));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty(ConnectionConstants.CACHE_SQL_LIMIT));
        ds = new HikariDataSource(config);
    }

    private MyDataSource() {
    }

    public static javax.sql.DataSource getDataSource() {
        return ds;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = MyDataSource.class
                .getClassLoader()
                .getResourceAsStream(ConnectionConstants.DB_SETTINGS_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}
