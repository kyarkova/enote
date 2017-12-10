package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Notebook;
import com.enote.entity.User;
import com.enote.service.NoteService;
import com.enote.service.NotebookService;
import com.enote.service.UserService;
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

    @Test
    public void testCountNotebooks() {
        long countNotebooks = notebookService.countNotebooks();
        assertEquals(8, countNotebooks);
    }

    @Test
    public void testCreate() {
        // TODO: better logins, names for entities, but they must be unique!
        User user = userService.getByLogin("user2");
        notebookService.create("testNotebookService", userService.save(user));
        final Collection<Notebook> test = notebookService.getByName("testNotebookService");
        assertNotNull(test);
        assertFalse(test.isEmpty());
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
    @Ignore
    public void testDeleteById() {
//        assertTrue(notebookService.findById(4L).isPresent());
//        assertTrue(noteService.findById(6L).isPresent()); // Note 6 is in Notebook 4
//        notebookService.deleteById(4L); // TODO: det rid of magic values
//        assertFalse(notebookService.findById(4L).isPresent());
//        assertFalse(noteService.findById(6L).isPresent());
    }

}
