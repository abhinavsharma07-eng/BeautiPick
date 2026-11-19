package com.app.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared database connection pool manager using HikariCP.
 * Strictly reads credentials from DB_URL, DB_USER, and DB_PASSWORD environment variables.
 */
public class DatabaseConfig {

    private static volatile HikariDataSource dataSource;
    private static final Object lock = new Object();

    public static boolean isConfigured() {
        return System.getenv("DB_URL") != null &&
               System.getenv("DB_USER") != null &&
               System.getenv("DB_PASSWORD") != null;
    }

    public static List<String> getMissingEnvVariables() {
        List<String> missing = new ArrayList<>();
        if (System.getenv("DB_URL") == null) missing.add("DB_URL");
        if (System.getenv("DB_USER") == null) missing.add("DB_USER");
        if (System.getenv("DB_PASSWORD") == null) missing.add("DB_PASSWORD");
        return missing;
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {
            synchronized (lock) {
                if (dataSource == null) {
                    List<String> missing = getMissingEnvVariables();
                    if (!missing.isEmpty()) {
                        throw new IllegalStateException("Database configuration error: Missing required environment variable(s): " + String.join(", ", missing));
                    }

                    String dbUrl = System.getenv("DB_URL");
                    String dbUser = System.getenv("DB_USER");
                    String dbPassword = System.getenv("DB_PASSWORD");

                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(dbUrl);
                    config.setUsername(dbUser);
                    config.setPassword(dbPassword);
                    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    config.setMaximumPoolSize(10);
                    config.setMinimumIdle(2);
                    config.setIdleTimeout(30000);
                    config.setConnectionTimeout(20000);

                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
