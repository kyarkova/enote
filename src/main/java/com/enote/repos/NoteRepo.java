package com.enote.repos;

import com.enote.entity.Note;

import java.util.Set;

public interface NoteRepo {

    Note findById(Long id);

    int createNote(String name, Long notebookId);

    Note findByIdWithTags(Long id);

    Set<Note> findByTagId(Long id);

    Set<Note> findByNotebookId(Long id);
}
