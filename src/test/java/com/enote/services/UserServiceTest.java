package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.User;
import com.enote.repo.UserRepo;
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
        int expectedCount = userRepo.findAll().size();
        long countUsers = userService.countUsers();
        assertEquals(expectedCount, countUsers);
    }

    @Test
    public void testCreateUser() {
        final String login = "testCreateUser";
        userService.create(login, "test");
        assertNotNull(userRepo.getByLogin(login));
    }

    @Test(expected = PersistenceException.class)
    public void testCreateuserNonUnique() {
        final String nonUniqueName = "nonUniqueName";
        final String password = "pass";
        userService.create(nonUniqueName, password);
        userService.create(nonUniqueName, password);
    }

    @Test
    public void testFindAll() {
        int expected = userRepo.findAll().size();
        int actual = userService.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById() {
        final User newUser = new User().setLogin("testFindById").setPassword("pass");
        Long id = userRepo.save(newUser).getId();
        Optional<User> user = userService.findById(id);
        assertTrue(user.isPresent());
    }

    @Test
    public void testDeletExistentUserById() {
        final User newUser = new User().setLogin("deleteExistingUser").setPassword("pass");
        Long id = userRepo.save(newUser).getId();
        Assert.assertTrue(userRepo.findById(id).isPresent());
        userService.deleteById(id);
        assertFalse(userRepo.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteNonexistentUserById() {
        final long nonexistentUserId = 99L;
        assertFalse(userRepo.findById(nonexistentUserId).isPresent());
        userService.deleteById(nonexistentUserId);
    }
}
