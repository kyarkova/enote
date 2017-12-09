package com.enote.repos.impl;

import com.enote.entity.Note;
import com.enote.entity.Tag;
import com.enote.repos.NoteRepo;
import com.enote.repos.NotebookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository("noteRepo")
public class NoteRepoImpl implements NoteRepo {
    @Autowired
    private RowMapper<Note> rowMapper;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    NotebookRepo notebookRepo;

    @Override
    public Note findById(Long id) {
        String sql = "SELECT id, title, notebook_id FROM note WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int createNote(String name, Long notebookId) {
        return jdbcTemplate.update(
                "INSERT INTO note(title, notebook_id) VALUES(?,?)", name, notebookId);
    }


    @Override
    public Note findByIdWithTags(Long id) {
        String sql = "Select id, title, notebook_id from note " +
                "where id IN " +
                "(SELECT note_id From note_tag where tag_id = " + id + ");";
        return jdbcTemplate.query(sql, new NoteWithTagsExtractor());
    }

    @Override
    public Set<Note> findByTagId(Long tagId) {
        String sql = "select id, title, notebook_id from note where id in " +
                "(select note_id from note_tag where tag_id = " + tagId + ")";

        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{tagId}, rowMapper));
    }

    @Override
    public Set<Note> findByNotebookId(Long notebookId) {
        String sql = "SELECT id, title, notebook_id FROM note WHERE notebook_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{notebookId}, rowMapper));
    }

    private class NoteWithTagsExtractor implements ResultSetExtractor<Note> {
        @Override
        public Note extractData(ResultSet rs) throws SQLException {
            Note note = null;
            while (rs.next()) {
                if (note == null) {
                    note = new Note();
                    note.setId(rs.getLong("ID"));
                    note.setTitle(rs.getString("TITLE"));
                    ;
//                    note.setNotebook(notebookRepo.findById(rs.getLong("NOTEBOOK_ID")));
                }
                Tag t = new Tag()
                        .setName(rs.getString("TITLE"));
                t.setId(rs.getLong("ID"));
                note.addTag(t);
            }
            return note;
        }
    }

}
