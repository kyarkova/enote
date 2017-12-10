package com.enote.repo;

import com.enote.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    Collection<Note> getByTitle(String title);

    @Query("select n from Note n inner join n.tags t where t.id = :id")
    Collection<Note> getByTagId(@Param("id") Long tagId);

    Collection<Note> getByNotebookId(Long notebookId);

    @Query("select count(id) from Note n")
    Long countNotes();
}
