package com.enote.repos.impl;

import com.enote.entity.Tag;
import com.enote.repos.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository("tagRepo")
public class TagRepoImpl implements TagRepo {
    @Autowired
    private RowMapper<Tag> rowMapper;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Tag findById(Long id) {
        String sql = "SELECT id, name FROM tag WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    @Override
    public int createTag(String name) {
        return jdbcTemplate.update(
                "INSERT INTO tag(name) VALUES(?)", name);
    }

    @Override
    public Set<Tag> findByNoteId(Long noteId) {
        String sql = "select id, name from tag where id in " +
                "(select note_id from note_tag where note_id = " + noteId + ")";

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }
}
