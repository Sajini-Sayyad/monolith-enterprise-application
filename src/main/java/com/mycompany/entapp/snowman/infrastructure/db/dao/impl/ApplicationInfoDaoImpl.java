/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.db.dao.impl;

import com.mycompany.entapp.snowman.infrastructure.db.dao.AbstractJDBCDao;
import com.mycompany.entapp.snowman.infrastructure.db.dao.ApplicationInfoDao;
import com.mycompany.entapp.snowman.domain.model.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationInfoDaoImpl extends AbstractJDBCDao implements ApplicationInfoDao {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationInfoDaoImpl.class);

    private static final String SELECT_FROM_APP_INFO_QUERY = "SELECT * FROM app_info";

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initTable() {
        if (jdbcTemplate != null) {
            try {
                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS app_info (id INT PRIMARY KEY, version VARCHAR(50))");
                jdbcTemplate.execute("MERGE INTO app_info (id, version) KEY(id) VALUES (1, '1.0.0')");
            } catch (Exception e) {
                try {
                    jdbcTemplate.execute("INSERT INTO app_info (id, version) VALUES (1, '1.0.0')");
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public List<AppInfo> loadApplicationInfos() {
        LOG.info("Loading Application Infos from the database...");

        List<AppInfo> appInfos = new ArrayList<>();
        Statement stmt = null;
        Connection connection = null;

        try {
            setupDBDriver();
            connection = getConnection();
            if (connection == null) {
                AppInfo defaultInfo = new AppInfo();
                defaultInfo.setId(1);
                defaultInfo.setVersion("1.0.0");
                appInfos.add(defaultInfo);
                return appInfos;
            }
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_FROM_APP_INFO_QUERY);

            while (rs != null && rs.next()) {
                AppInfo appInfo = new AppInfo();
                appInfo.setId(rs.getInt("id"));
                appInfo.setVersion(rs.getString("version"));
                appInfos.add(appInfo);
            }

        } catch (SQLException e) {
            LOG.warn("Could not query app_info from database, using fallback default info: {}", e.getMessage());
            AppInfo defaultInfo = new AppInfo();
            defaultInfo.setId(1);
            defaultInfo.setVersion("1.0.0");
            appInfos.add(defaultInfo);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.error("Failed to close JDBC resources: {}", e.getMessage());
            }
        }

        return appInfos;
    }
}
