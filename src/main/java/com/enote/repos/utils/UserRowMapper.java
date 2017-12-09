package com.enote.repos.utils;

import com.enote.entity.User;
import com.enote.repos.NotebookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserRowMapper implements RowMapper<User> {

    @Autowired
    NotebookRepo notebookRepo;

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("ID");
        String login = rs.getString("LOGIN");
        String password = rs.getString("PASSWORD");
        User user = new User()
                .setLogin(login)
                .setPassword(password)
                .setNotebooks(notebookRepo.findByUserId(id));
        user.setId(id);
        return user;
    }
}