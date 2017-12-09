package com.enote.repos.impl;

import com.enote.entity.Notebook;
import com.enote.repos.NotebookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository("notebookRepo")
public class NotebookRepoImpl implements NotebookRepo {
    @Autowired
    private RowMapper<Notebook> rowMapper;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public NotebookRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Notebook findById(Long id) {
        String sql = "SELECT id, name, user_id FROM notebook WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    @Override
    public int createNotebook(String name, Long userId) {
        return jdbcTemplate.update(
                "INSERT INTO notebook(NAME, USER_ID) VALUES(?,?)", name, userId);
    }

    @Override
    public Set<Notebook> findByUserId(Long id) {
        String sql = "select id, name, user_id from notebook where user_id = " + id;
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }
}
