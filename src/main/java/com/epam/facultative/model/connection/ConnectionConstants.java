package com.epam.facultative.model.connection;

/**
 * Keyholder for properties to configure database connection
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */

class ConnectionConstants {
    static String URL_PROPERTY = "connection.url";
    static String USER_NAME_PROPERTY = "user.name";
    static String PASSWORD_PROPERTY = "password";
    static String DRIVER_PROPERTY = "driver";
    static String CACHE_PREPARED_STATEMENT = "cache.prep.stmts";
    static String CACHE_SIZE = "prep.stmt.cache.size";
    static String CACHE_SQL_LIMIT = "prep.stmt.cache.sql.limit";
}
