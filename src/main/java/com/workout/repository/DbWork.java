package com.workout.repository;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbWork {
    private static final String URL = "jdbc:postgresql://localhost:5432/workout_db";
    private static final String USER = "elise";
    private static final String PASSWORD = "superuser";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {

        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DbWork() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}


