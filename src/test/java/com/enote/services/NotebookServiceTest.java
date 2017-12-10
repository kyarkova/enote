package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Notebook;
import com.enote.entity.User;
import com.enote.repo.NotebookRepo;
import com.enote.repo.UserRepo;
import com.enote.service.NoteService;
import com.enote.service.NotebookService;
import com.enote.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class NotebookServiceTest {

    @Autowired
    NotebookService notebookService;

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    NotebookRepo notebookRepo;

    @Test
    public void testCountNotebooks() {
        int expectedCount = notebookRepo.findAll().size();
        long countNotebooks = notebookService.countNotebooks();
        assertEquals(expectedCount, countNotebooks);
    }

    @Test
    public void testCreateWithNonExistingName() {
        final User user = new User().setLogin("testCreateWithNonExistingName").setPassword("pass");
        notebookService.create("testCreateWithNonExistingName", userService.save(user));
        final Collection<Notebook> test = notebookService.getByName("testCreateWithNonExistingName");
        assertNotNull(test);
        assertFalse(test.isEmpty());
    }

    @Test(expected = PersistenceException.class)
    public void createNotebookWithExistingName() {
        final User sameUser = new User()
                .setLogin("testCreateNotebookWithExistingName")
                .setPassword("pass");
        userRepo.save(sameUser);
        final String sameName = "sameName";
        notebookService.create(sameName, sameUser);
        notebookService.create(sameName, sameUser);
    }

    @Test
    public void testFindAll() {
        List<Notebook> all = notebookService.findAll();
        assertNotNull(all);
    }

    @Test
    public void testFindByUser() {
        User user = userService.getByLogin("user1");
        List<Notebook> list = notebookService.findAllByUser(user);
        assertNotNull(list);
    }

    @Test
    public void testDeleteExistingNotebook() {
        final User newUser = new User().setLogin("testDeleteExistingNotebook").setPassword("pass");
        userRepo.save(newUser);
        final Notebook newNotebook = new Notebook().setName("testDeleteExistingNotebook").setUser(newUser);
        final long id = notebookRepo.save(newNotebook).getId();
        Assert.assertTrue(notebookService.findById(id).isPresent());
        notebookService.deleteById(id);
        assertFalse(notebookService.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteNonexistentNotebookById() {
        final long nonexistentNotebookId = 99L;
        assertFalse(notebookService.findById(nonexistentNotebookId).isPresent());
        notebookService.deleteById(nonexistentNotebookId);
    }
}
