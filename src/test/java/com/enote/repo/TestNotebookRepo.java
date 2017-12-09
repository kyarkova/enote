package com.enote.repo;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
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
public class TestNotebookRepo {

    @Autowired
    NotebookRepo notebookRepo;

    @Autowired
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(notebookRepo);
        assertNotNull(userRepo);
    }

    @Test
    public void createNonexistentNotebook() {
        final User user = userRepo.save(new User().setLogin("createNonexistentNotebook").setPassword("pass"));
        assertNotNull(notebookRepo.save(new Notebook().setName("createNonexistentNotebook").setUser(user)));
    }

    @Test(expected = PersistenceException.class)
    public void createWithoutUser() {
        notebookRepo.save(new Notebook().setName("invalid notebook"));
    }

    @Test(expected = PersistenceException.class)
    public void createNotebookWithExistingNameAndUser() {
        final String name = "another name";
        final User user = userRepo.save(new User().setLogin("createNotebookWithExistingNameAndUser").setPassword("pass"));
        notebookRepo.save(new Notebook().setName(name).setUser(user));
        notebookRepo.save(new Notebook().setName(name).setUser(user));
    }

    @Test
    @Transactional(timeout = 10)
    public void updateName() {
        final long id = 4L; // TODO: get rid of "magic" values like this
        final Notebook notebook = notebookRepo.getOne(id);
        final String newName = "new name";
        notebookRepo.save(notebook.setName(newName));
        final Notebook updatedNotebook = notebookRepo.getOne(id);
        assertEquals(notebook.getName(), updatedNotebook.getName());
        assertEquals(notebook.getUser(), updatedNotebook.getUser());
        assertEquals(notebook.getNotes().size(), updatedNotebook.getNotes().size());
        // TODO: check note sets equality
        assertEquals(updatedNotebook.getName(), newName);
    }

    @Test(expected = PersistenceException.class)
    public void updateWithExistingName() {
        final Notebook notebook = notebookRepo.getOne(1L);
        notebookRepo.save(notebook.setName("notebook1-3"));
    }

    @Test
    public void deleteExistingNotebook() {
        final User newUser = new User().setLogin("deleteExistingNotebook").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("deleteExistingNotebook").setUser(newUser);
        final long id = notebookRepo.save(newNotebook).getId();
        assertTrue(notebookRepo.findById(id).isPresent());
        final Notebook notebook = notebookRepo.getOne(id);
        notebookRepo.delete(notebook);
        assertFalse(notebookRepo.findById(id).isPresent());
    }

    @Test(expected = PersistenceException.class)
    public void deleteNonexistentNotebook() {
        notebookRepo.delete(new Notebook());
    }

    @Test
    public void deleteNotebookByExistingId() {
        final User newUser = new User().setLogin("deleteNotebookByExistingId").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("deleteNotebookByExistingId").setUser(newUser);
        final long id = notebookRepo.save(newNotebook).getId();
        assertTrue(notebookRepo.findById(id).isPresent());
        notebookRepo.deleteById(id);
        assertFalse(notebookRepo.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteByNonexistentId() {
        notebookRepo.deleteById(Long.MAX_VALUE);
    }

    @Test
    public void findByExistingId() {
        final Optional<Notebook> optionalNotebook = notebookRepo.findById(1L);
        assertTrue(optionalNotebook.isPresent());
        assertEquals("notebook1-1", optionalNotebook.get().getName());
    }

    @Test
    public void findByNonexistentId() {
        assertFalse(notebookRepo.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    @Transactional(timeout = 10)
    public void getOneExisting() {
        final Notebook notebook = notebookRepo.getOne(1L);
        assertEquals("notebook1-1", notebook.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional(timeout = 10)
    public void getOneNonexistent() {
        assertNull(notebookRepo.getOne(Long.MAX_VALUE));
    }

    @Test
    public void findByExistingName() {
        final String name = "notebook1-1";
        final Collection<Notebook> notebooks = notebookRepo.getByName(name);
        assertEquals(notebooks.size(), 1);
        final Notebook notebook = notebooks.iterator().next();
        assertEquals(name, notebook.getName());
        assertEquals("user1", notebook.getUser().getLogin());
    }

    @Test
    public void findByNonexistentName() {
        assertTrue(notebookRepo.getByName("notebook404").isEmpty());
    }

    @Test
    @Transactional(timeout = 10)
    public void getByExistingUserId() {
        final Long userId = 1L;
        final Collection<Notebook> notebooks = notebookRepo.getByUserId(userId);
        assertFalse(notebooks.isEmpty());
        assertEquals(notebooks.size(), 3);
    }

    @Test
    @Transactional(timeout = 10)
    public void getByNonexistentUserId() {
        assertTrue(notebookRepo.getByUserId(Long.MAX_VALUE).isEmpty());
    }

}
