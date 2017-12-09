package com.enote.repo;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Note;
import com.enote.entity.Tag;
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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class TestTagRepo {

    @Autowired
    TagRepo tagRepo;

    @Before
    public void setUp() {
        assertNotNull(tagRepo);
    }

    @Test
    public void createNonexistentTag() {
        assertNotNull(tagRepo.save(new Tag().setName("name")));
    }

    @Test(expected = PersistenceException.class)
    public void createWithExistingName() {
        tagRepo.save(new Tag().setName("tag1"));
    }

    @Test
    @Transactional(timeout = 10)
    public void updateName() {
        final long id = 6L; // TODO: get rid of "magic" values like this
        final Tag tag = tagRepo.getOne(id);
        final String newName = "new name";
        tagRepo.save(tag.setName(newName));
        final Tag updatedTag = tagRepo.getOne(id);
        assertEquals(tag.getName(), updatedTag.getName());
        // TODO: check note sets equality
        assertEquals(updatedTag.getName(), newName);
    }

    @Test
    public void deleteExistingTag() {
        final Tag newTag = new Tag().setName("deleteExistingTag");
        final long id = tagRepo.save(newTag).getId();
        assertTrue(tagRepo.findById(id).isPresent());
        final Tag tag = tagRepo.getOne(id);
        tagRepo.delete(tag);
        assertFalse(tagRepo.findById(id).isPresent());
    }

    @Test(expected = PersistenceException.class)
    public void deleteNonexistentTag() {
        tagRepo.delete(new Tag());
    }

    @Test
    public void deleteTagByExistingId() {
        final Tag newTag = new Tag().setName("deleteTagByExistingId");
        final long id = tagRepo.save(newTag).getId();
        assertTrue(tagRepo.findById(id).isPresent());
        tagRepo.deleteById(id);
        assertFalse(tagRepo.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteByNonexistentId() {
        tagRepo.deleteById(Long.MAX_VALUE);
    }

    @Test
    public void findByExistingId() {
        final Optional<Tag> optionalNote = tagRepo.findById(1L);
        assertTrue(optionalNote.isPresent());
        assertEquals("tag1", optionalNote.get().getName());
    }

    @Test
    public void findByNonexistentId() {
        assertFalse(tagRepo.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    @Transactional(timeout = 10)
    public void getOneExisting() {
        final Tag note = tagRepo.getOne(1L);
        assertEquals("tag1", note.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional(timeout = 10)
    public void getOneNonexistent() {
        assertNull(tagRepo.getOne(Long.MAX_VALUE));
    }

    @Test
    public void findByExistingName() {
        final String name = "tag7";
        final Collection<Tag> tags = tagRepo.getByName(name);
        assertEquals(tags.size(), 1);
        final Tag tag = tags.iterator().next();
        assertEquals(name, tag.getName());
        final Set<Note> notes = tag.getNotes();
        assertEquals(notes.size(), 1);
        final Note note = notes.iterator().next();
        assertEquals("note2-1", note.getTitle());
    }

    @Test
    public void findByNonexistentTitle() {
        assertTrue(tagRepo.getByName("tag404").isEmpty());
    }

    @Test
    public void getByExistingNoteId() {
        final long noteId = 4L;
        final Collection<Tag> tags = tagRepo.getByNoteId(noteId);
        assertFalse(tags.isEmpty());
        assertEquals(tags.size(), 1);
        final Tag tag = tags.iterator().next();
        assertEquals("tag1", tag.getName());
    }

    @Test
    public void getByNonexistentNoteId() {
        assertTrue(tagRepo.getByNoteId(Long.MAX_VALUE).isEmpty());
    }

}
