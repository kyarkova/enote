package com.enote.repos.utils;

import com.enote.entity.Note;
import com.enote.repos.NotebookRepo;
import com.enote.repos.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class NoteRowMapper implements RowMapper<Note> {

    @Autowired
    NotebookRepo notebookRepo;

    @Autowired
    TagRepo tagRepo;

    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("ID");
        String name = rs.getString("TITLE");
        Long notebookId = rs.getLong("NOTEBOOK_ID");
        Note note = new Note()
                .setTitle(name)
//                .setNotebook(notebookRepo.findById(notebookId))
                .setTags(tagRepo.findByNoteId(id));
        note.setId(id);
        return note;
    }
}
