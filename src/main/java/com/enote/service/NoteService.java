package com.enote.service;

import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.entity.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NoteService {

    Long countNotes();

    void create(String title, Notebook notebook);

    List<Note> findAll();

    Optional<Note> findById(Long id);



    Collection<Note> findAllByNotebook(Notebook notebook);

    void addTag(Note note, Set<Tag> tags);

    Collection<Note> findByTagId(Long tagId);

    void deleteById(Long id);

    void addTag(Note note, Tag tag);
}
