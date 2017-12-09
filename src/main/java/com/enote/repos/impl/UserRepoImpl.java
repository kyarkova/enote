package com.enote.repos.impl;

import com.enote.entity.User;
import com.enote.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository("userRepo")
public class UserRepoImpl implements UserRepo {
    @Autowired
    private RowMapper<User> rowMapper;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User findById(Long id) {
        String sql = "SELECT id, login, password FROM user WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    @Override
    public int createUser(String login, String password) {
        return jdbcTemplate.update(
                "INSERT INTO user(LOGIN, PASSWORD) VALUES(?,?)", login, password);
    }

    @Override
    public Set<User> findAll() {
        String sql = "SELECT id, login, password FROM user";
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }

    @Override
    public int deleteById(long userId) {
        return jdbcTemplate.update(
                "delete from user where id =? ",
                userId);
    }

    @Override
    public int updatePassword(long id, String newPass) {
        String sql = "update user set password=? where ID = ?";
        return jdbcTemplate.update(sql, newPass, id);
    }

    @Override
    public Set<User> findAllByLogin(String login, boolean exactMatch) {
        String sql = "select id, login, password from user where ";
        if (exactMatch) {
            sql += "login= ?";
        } else {
            sql += "login like '%' || ? || '%'";
        }
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{login}, rowMapper));
    }
}
