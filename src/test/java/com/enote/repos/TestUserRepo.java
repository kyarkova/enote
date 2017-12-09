package com.enote.repos;

import com.enote.config.AppConfig;
import com.enote.config.TestDataConfig;
import com.enote.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
public class TestUserRepo {

    @Autowired
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(userRepo);
    }

    @Test
    public void testFindById() {
        User user = userRepo.findById(1L);
        assertEquals("user1", user.getLogin());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        User user = userRepo.findById(99L);
        assertEquals("someuser", user.getLogin());
    }

    @Test
    public void testCreate() {
        int result = userRepo.createUser("new", "test");
        assertEquals(1, result);
        Set<User> newUser = userRepo.findAllByLogin("new", true);
        assertTrue(newUser.size() == 1);
    }

    @Test
    public void testUpdate() {
        int result = userRepo.updatePassword(1L, "newpass");
        assertEquals(1, result);
    }

    @Test
    public void testDelete() {
        int result = userRepo.deleteById(4L);
        assertEquals(1, result);
    }


}
