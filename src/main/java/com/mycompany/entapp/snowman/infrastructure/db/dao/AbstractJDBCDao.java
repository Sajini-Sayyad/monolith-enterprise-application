/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJDBCDao.class);

    @Autowired(required = false)
    private DataSource dataSource;

    private static final String DATABASE_HOST = "localhost";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE = "snowman";

    private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://" + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE;
    private static final String DATABASE_USERNAME = "username";
    private static final String DATABASE_PASSWORD = "password";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected void setupDBDriver() {
        if (dataSource == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    LOG.warn("MySQL driver not found, continuing if DataSource is configured");
                }
            }
        }
    }

    protected Connection getConnection() {
        if (dataSource != null) {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                LOG.error("Failed to obtain connection from DataSource: {}", e.getMessage());
            }
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            LOG.error("Failed to connect via DriverManager fallback: {}", e.getMessage());
        }

        return connection;
    }
}
