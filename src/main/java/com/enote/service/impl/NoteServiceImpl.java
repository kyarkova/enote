package com.enote.service.impl;

import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.entity.Tag;
import com.enote.repo.NoteRepo;
import com.enote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class NoteServiceImpl implements NoteService {

    private NoteRepo noteRepo;

    @Autowired
    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public long countNotes() {
        return noteRepo.countNotes();
    }

    @Override
    @Transactional(readOnly = false)
    public void create(String title, Notebook notebook) {
        Note note = new Note()
                .setTitle(title)
                .setNotebook(notebook);

        noteRepo.save(note);
    }

    @Override
    public List<Note> findAll() {
        return noteRepo.findAll();
    }

    @Override
    public Collection<Note> findAllByNotebook(Notebook notebook) {
        return noteRepo.getByNotebookId(notebook.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void addTag(Note note, Set<Tag> tags) {
        note.setTags(tags);
        noteRepo.save(note);
    }

    @Override
    public Collection<Note> findByTagId(Long tagId) {
        return noteRepo.getByTagId(tagId);
    }


    @Override
    public void deleteById(Long id) {
        noteRepo.deleteById(id);
    }

    @Override
    public void addTag(Note note, Tag tag) {
        note.getTags().add(tag);
    }
}
