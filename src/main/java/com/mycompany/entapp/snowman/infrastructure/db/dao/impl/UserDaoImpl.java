/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.db.dao.impl;

import com.mycompany.entapp.snowman.infrastructure.db.dao.UserDao;
import com.mycompany.entapp.snowman.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String GET_USER_WITH_USERID_QUERY = "SELECT * FROM \"user\" where id = ?";
    private static final String GET_USER_WITH_USERID_FALLBACK_QUERY = "SELECT * FROM user where id = ?";
    private static final String DELETE_USER_WITH_USERID = "DELETE FROM \"user\" where id = ?";
    private static final String DELETE_USER_WITH_USERID_FALLBACK = "DELETE FROM user where id = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO \"user\" (id, firstname, lastname, username, password, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_FALLBACK_QUERY = "INSERT INTO user (id, firstname, lastname, username, password, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE \"user\" SET firstname = ?, lastname = ?, username = ?, password = ?, email = ? WHERE id = ?";
    private static final String UPDATE_USER_FALLBACK_QUERY = "UPDATE user SET firstname = ?, lastname = ?, username = ?, password = ?, email = ? WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initTable() {
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"user\" (id INT PRIMARY KEY, firstname VARCHAR(50), lastname VARCHAR(50), username VARCHAR(50), password VARCHAR(50), email VARCHAR(50))");
        } catch (Exception e) {
            try {
                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY, firstname VARCHAR(50), lastname VARCHAR(50), username VARCHAR(50), password VARCHAR(50), email VARCHAR(50))");
            } catch (Exception ignored) {
            }
        }
    }

    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    @Override
    public User findUser(int userId) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_WITH_USERID_QUERY, userRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            try {
                return jdbcTemplate.queryForObject(GET_USER_WITH_USERID_FALLBACK_QUERY, userRowMapper, userId);
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
        }
    }

    @Override
    public void saveUser(User user) {
        if (user == null) {
            return;
        }
        User existing = findUser(user.getUserId());
        if (existing != null) {
            try {
                jdbcTemplate.update(UPDATE_USER_QUERY, user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUserId());
            } catch (Exception ex) {
                jdbcTemplate.update(UPDATE_USER_FALLBACK_QUERY, user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUserId());
            }
        } else {
            try {
                jdbcTemplate.update(INSERT_USER_QUERY, user.getUserId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getEmail());
            } catch (Exception ex) {
                jdbcTemplate.update(INSERT_USER_FALLBACK_QUERY, user.getUserId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getEmail());
            }
        }
    }

    @Override
    public void removeUser(int userId) {
        try {
            jdbcTemplate.update(DELETE_USER_WITH_USERID, userId);
        } catch (Exception ex) {
            jdbcTemplate.update(DELETE_USER_WITH_USERID_FALLBACK, userId);
        }
    }
}
