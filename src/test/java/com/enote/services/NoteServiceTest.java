package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.entity.Tag;
import com.enote.repo.NoteRepo;
import com.enote.repo.NotebookRepo;
import com.enote.repo.TagRepo;
import com.enote.service.NoteService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void testCountNotes() {
        long countNotes = noteService.countNotes();
        assertEquals(10, countNotes);
    }

    @Test
    public void testCreate() {
        Notebook notebook = notebookRepo.getOne(1L);
        // FIXME: do not create entities with same unique field values in different tests!
        noteService.create("testCreateNoteService", notebook);
        assertNotNull(noteRepo.getByTitle("testCreateNoteService"));
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
    @Ignore
    public void testDeleteById() {
        // FIXME: do not delete entities with ids that are used in other tests
        // FIXME: you can create a new one here like in RepoTest and then delete it
//        noteService.deleteById(4L);
//        assertNull(noteRepo.getOne(4L));
    }

    @Test
    @Transactional(timeout = 10)
    public void testAddTag() {
        Note note = noteRepo.getOne(1L);
        Tag tag = tagRepo.getOne(1L);
        noteService.addTag(note, tag);
        assertTrue(note.getTags().contains(tag));
    }
}
