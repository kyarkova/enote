package com.enote.repos.utils;

import com.enote.entity.Tag;
import com.enote.repos.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TagRowMapper implements RowMapper<Tag> {

    @Autowired
    NoteRepo noteRepo;

    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        Tag tag = new Tag()
                .setName(name);
//                .setNotes(noteRepo.findByTagId(id));
        tag.setId(id);
        return tag;
    }
}
