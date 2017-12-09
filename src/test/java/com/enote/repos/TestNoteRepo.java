package com.enote.repos;

import com.enote.config.AppConfig;
import com.enote.config.TestDataConfig;
import com.enote.entity.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
public class TestNoteRepo {

    @Autowired
    NoteRepo noteRepo;

    @Before
    public void setUp() {
        assertNotNull(noteRepo);
    }

    @Test
    public void testFindById() {
        Note note = noteRepo.findById(1L);
        assertEquals("note1-1", note.getTitle());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        Note note = noteRepo.findById(99L);
        assertEquals("somenote", note.getTitle());
    }

    @Test
    public void testExtractor() {
        Note note = noteRepo.findByIdWithTags(1L);
        assertEquals(4, note.getTags().size());
    }
}
