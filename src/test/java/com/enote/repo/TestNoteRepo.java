package com.enote.repo;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class TestNoteRepo {

    @Autowired
    NoteRepo noteRepo;

    @Autowired
    NotebookRepo notebookRepo;

    @Autowired
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(noteRepo);
        assertNotNull(notebookRepo);
        assertNotNull(userRepo);
    }

    @Test
    public void createNonexistentNote() {
        final User user = userRepo.save(new User().setLogin("createNonexistentNote").setPassword("pass"));
        final Notebook notebook = notebookRepo.save(new Notebook().setName("createNonexistentNote").setUser(user));
        assertNotNull(noteRepo.save(new Note().setTitle("createNonexistentNote").setNotebook(notebook)));
    }

    @Test(expected = PersistenceException.class)
    public void createWithoutNotebook() {
        noteRepo.save(new Note().setTitle("invalid note"));
    }

    @Test
    @Transactional(timeout = 10)
    public void updateTitle() {
        final long id = 4L; // TODO: get rid of "magic" values like this
        final Note note = noteRepo.getOne(id);
        final String newTitle = "new title";
        noteRepo.save(note.setTitle(newTitle));
        final Note updatedNote = noteRepo.getOne(id);
        assertEquals(note.getTitle(), updatedNote.getTitle());
        assertEquals(note.getNotebook(), updatedNote.getNotebook());
        assertEquals(note.getTags().size(), updatedNote.getTags().size());
        // TODO: check tag sets equality
        assertEquals(updatedNote.getTitle(), newTitle);
    }

    @Test
    public void deleteExistingNote() {
        final User newUser = new User().setLogin("deleteExistingNote").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("deleteExistingNote").setUser(newUser);
        notebookRepo.save(newNotebook);
        final long id = noteRepo.save(new Note().setTitle("deleteExistingNote").setNotebook(newNotebook)).getId();
        assertTrue(noteRepo.findById(id).isPresent());
        final Note note = noteRepo.getOne(id);
        noteRepo.delete(note);
        assertFalse(noteRepo.findById(id).isPresent());
    }

    @Test(expected = PersistenceException.class)
    public void deleteNonexistentNote() {
        noteRepo.delete(new Note());
    }

    @Test
    public void deleteNoteByExistingId() {
        final User newUser = new User().setLogin("deleteNoteByExistingId").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("deleteNoteByExistingId").setUser(newUser);
        notebookRepo.save(newNotebook);
        final long id = noteRepo.save(new Note().setTitle("deleteNoteByExistingId").setNotebook(newNotebook)).getId();
        assertTrue(noteRepo.findById(id).isPresent());
        noteRepo.deleteById(id);
        assertFalse(noteRepo.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteByNonexistentId() {
        noteRepo.deleteById(Long.MAX_VALUE);
    }

    @Test
    public void findByExistingId() {
        final Optional<Note> optionalNote = noteRepo.findById(1L);
        assertTrue(optionalNote.isPresent());
        assertEquals("note1-1", optionalNote.get().getTitle());
    }

    @Test
    public void findByNonexistentId() {
        assertFalse(noteRepo.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    @Transactional(timeout = 10)
    public void getOneExisting() {
        final Note note = noteRepo.getOne(1L);
        assertEquals("note1-1", note.getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional(timeout = 10)
    public void getOneNonexistent() {
        assertNull(noteRepo.getOne(Long.MAX_VALUE));
    }

    @Test
    public void findByExistingTitle() {
        final String title = "note1-1";
        final Collection<Note> notes = noteRepo.getByTitle(title);
        assertEquals(notes.size(), 1);
        final Note note = notes.iterator().next();
        assertEquals(title, note.getTitle());
        assertEquals("notebook1-1", note.getNotebook().getName());
    }

    @Test
    public void findByNonexistentTitle() {
        assertTrue(noteRepo.getByTitle("note404").isEmpty());
    }

    @Test
    @Transactional(timeout = 10)
    public void getByExistingNotebookId() {
        final Long notebookId = 3L;
        final Collection<Note> notes = noteRepo.getByNotebookId(notebookId);
        assertFalse(notes.isEmpty());
        assertEquals(notes.size(), 1);
        final Note note = notes.iterator().next();
        assertEquals("note3-1", note.getTitle());
    }

    @Test
    public void getByNonexistentNotebookId() {
        assertTrue(noteRepo.getByNotebookId(Long.MAX_VALUE).isEmpty());
    }

    @Test
    public void getByExistingTagId() {
        final long tagId = 7L;
        final Collection<Note> notes = noteRepo.getByTagId(tagId);
        assertFalse(notes.isEmpty());
        assertEquals(notes.size(), 1);
        final Note note = notes.iterator().next();
        assertEquals("note2-1", note.getTitle());
    }

    @Test
    public void getByNonexistentTagId() {
        assertTrue(noteRepo.getByTagId(Long.MAX_VALUE).isEmpty());
    }

}
