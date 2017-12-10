package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.User;
import com.enote.repo.UserRepo;
import com.enote.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

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
        userService.create("loginUserTest", "test");
        assertNotNull(userRepo.getByLogin("loginUserTest"));
    }

    @Test
    public void testFindAll() {
        List<User> all = userService.findAll();
        assertNotNull(all);
    }

    @Test
    public void testFindById() {
        Optional<User> user = userService.findById(1L);
        assertTrue(user.isPresent());
    }

    @Test
    @Ignore
    public void testDeleteById() {
//        userService.deleteById(4L);
//        assertNull(userRepo.getOne(4L));
    }
}
