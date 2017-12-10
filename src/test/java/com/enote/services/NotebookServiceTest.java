package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Notebook;
import com.enote.entity.User;
import com.enote.repo.NotebookRepo;
import com.enote.repo.UserRepo;
import com.enote.service.NotebookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class NotebookServiceTest {

    @Autowired
    NotebookService notebookService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    NotebookRepo notebookRepo;

    @Test
    public void testCountNotebooks() {
        long countNotebooks = notebookService.countNotebooks();
        assertEquals(8, countNotebooks);
    }

    @Test
    public void testCreate() {
        User user = userRepo.getByLogin("user1");
        notebookService.create("test", user);
        assertNotNull(notebookRepo.getByName("test"));
    }

    @Test
    public void testFindAll() {
        List<Notebook> all = notebookService.findAll();
        assertNotNull(all);
    }

    @Test
    public void testFindByUser() {
        User user = userRepo.getByLogin("user1");
        List<Notebook> list = notebookService.findAllByUser(user);
        assertNotNull(list);
    }

    @Test
    public void testDeleteById() {
        notebookService.deleteById(4L);
        assertNull(notebookRepo.getOne(4L));
    }

}
