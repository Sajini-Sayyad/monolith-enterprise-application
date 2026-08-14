/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.db.health;

import com.mycompany.entapp.snowman.infrastructure.db.dao.AbstractJDBCDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DBHealthCheck extends AbstractJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(DBHealthCheck.class);

    private static final String SELECT_MIN_1_FROM_APP_INFO = "SELECT 1";

    public boolean getDBStatus() {
        Statement stmt = null;
        Connection connection = null;

        try {
            setupDBDriver();
            connection = getConnection();
            if (connection == null) {
                return false;
            }
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_MIN_1_FROM_APP_INFO);

            if (rs != null && rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            LOG.error("Database health check query failed: {}", e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.error("Failed to close connection/statement: {}", e.getMessage());
            }
        }

        return false;
    }
}
