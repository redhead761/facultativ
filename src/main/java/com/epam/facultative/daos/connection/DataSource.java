package com.epam.facultative.daos.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.epam.facultative.daos.connection.ConnectionConstants.*;

public class DataSource {
    private static final Logger logger = LoggerFactory.getLogger(DataSource.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;


    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME_PROPERTY));
        config.setPassword(properties.getProperty(PASSWORD_PROPERTY));
        config.setDriverClassName(properties.getProperty(DRIVER_PROPERTY));

        config.addDataSourceProperty("cachePrepStmts", properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty(CACHE_SQL_LIMIT));
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
                .getResourceAsStream(ConnectionConstants.DB_SETTINGS_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}
