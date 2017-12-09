package com.enote.repos.utils;

import com.enote.entity.Notebook;
import com.enote.repos.NoteRepo;
import com.enote.repos.NotebookRepo;
import com.enote.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class NotebookRowMapper implements RowMapper<Notebook> {
    @Autowired
    UserRepo userRepo;

    @Autowired
    NotebookRepo notebookRepo;

    @Autowired
    NoteRepo noteRepo;

    public Notebook mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        Long user = rs.getLong("USER_ID");
        Notebook notebook = new Notebook()
                .setName(name)
//                .setUser(userRepo.findById(user))
                .setNotes(noteRepo.findByNotebookId(id));
        notebook.setId(id);
        return notebook;
    }
}