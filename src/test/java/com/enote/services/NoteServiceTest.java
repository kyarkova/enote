package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.entity.Tag;
import com.enote.entity.User;
import com.enote.repo.NoteRepo;
import com.enote.repo.NotebookRepo;
import com.enote.repo.TagRepo;
import com.enote.repo.UserRepo;
import com.enote.service.NoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class NoteServiceTest {

    @Autowired
    NoteService noteService;

    @Autowired
    NotebookRepo notebookRepo;

    @Autowired
    NoteRepo noteRepo;

    @Autowired
    TagRepo tagRepo;

    @Autowired
    UserRepo userRepo;

    @Test
    public void testCountNotes() {
        int expectedCount = noteRepo.findAll().size();
        long countNotes = noteService.countNotes();
        assertEquals(expectedCount, countNotes);
    }

    @Test
    public void testCreate() {
        final User newUser = new User().setLogin("testCreateNote").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("testCreateNote").setUser(newUser);
        notebookRepo.save(newNotebook);
        noteRepo.save(new Note().setTitle("testCreateNote").setNotebook(newNotebook));
        assertNotNull(noteRepo.getByTitle("testCreateNote"));
    }

    @Test(expected = PersistenceException.class)
    public void testCreateNotUnique() {
        final User newUser = new User().setLogin("testCreateNoteNotUnique").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook()
                .setName("testCreateNoteNotUnique")
                .setUser(newUser);
        notebookRepo.save(newNotebook);
        noteRepo.save(new Note().setTitle("testCreateNoteNotUnique").setNotebook(newNotebook));
        noteRepo.save(new Note().setTitle("testCreateNoteNotUnique").setNotebook(newNotebook));
    }

    @Test
    public void testFindAll() {
        List<Note> all = noteService.findAll();
        assertNotNull(all);
    }

    @Test
    public void testfindByTagId() {
        Collection<Note> res = noteService.findByTagId(1L);
        assertNotNull(res);
    }

    @Test
    public void testDeleteExistingNoteById() {
        final User newUser = new User().setLogin("deleteExistingNote").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("deleteExistingNote").setUser(newUser);
        notebookRepo.save(newNotebook);
        final long id = noteRepo.save(new Note().setTitle("deleteExistingNote").setNotebook(newNotebook)).getId();
        assertTrue(noteRepo.findById(id).isPresent());
        noteService.deleteById(id);
        assertFalse(noteRepo.findById(id).isPresent());
    }

    @Test(expected = PersistenceException.class)
    public void deleteNonexistentNoteById() {
        final long nonexistentNoteId = 99L;
        assertFalse(noteRepo.findById(nonexistentNoteId).isPresent());
        noteService.deleteById(nonexistentNoteId);
    }

    @Test
    @Transactional(timeout = 10)
    public void testAddTag() {
        final long noteId = 1L;
        final Note note = noteRepo.getOne(noteId);
        final long tagId = 1L;
        final Tag tag = tagRepo.getOne(tagId);
        noteService.addTag(note, tag);
        assertTrue(note.getTags().contains(tag));
    }
}
