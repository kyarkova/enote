package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.User;
import com.enote.repo.UserRepo;
import com.enote.service.UserService;
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
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;


    @Test
    public void testCountUsers() {
        long countUsers = userService.countUsers();
        assertEquals(4, countUsers);
    }


    @Test
    public void testCreateUser() {
        userService.create("login", "test");
        assertNotNull(userRepo.getByLogin("login"));
    }

    @Test
    public void testFindAll() {
        List<User> all = userService.findAll();
        assertNotNull(all);
    }

    @Test
    public void testFindById() {
        User user = userService.findById(1L);
        assertNotNull(user);
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(4L);
        assertNull(userRepo.getOne(4L));
    }
}
