package com.enote.repo;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class TestUserRepo {

    @Autowired
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(userRepo);
    }

    @Test
    public void createNonexistentUser() {
        assertNotNull(userRepo.save(new User().setLogin("createNonexistentUser").setPassword("pass")).getId());
    }

    @Test(expected = PersistenceException.class)
    public void createUserWithExistingLogin() {
        final String login = "createUserWithExistingLogin";
        userRepo.save(new User().setLogin(login).setPassword("pass"));
        userRepo.save(new User().setLogin(login).setPassword("another pass"));
    }

    @Test
    @Transactional(timeout = 10)
    public void updatePassword() {
        final long id = 4L; // TODO: get rid of "magic" values like this
        final User user = userRepo.getOne(id);
        final String newPassword = "new password";
        userRepo.save(user.setPassword(newPassword));
        final User updatedUser = userRepo.getOne(id);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getNotebooks().size(), updatedUser.getNotebooks().size());
        // TODO: check notebook sets equality
        //assertTrue(user.getNotebooks().containsAll(updatedUser.getNotebooks()));
        //assertTrue(updatedUser.getNotebooks().containsAll(user.getNotebooks()));
        assertEquals(updatedUser.getPassword(), newPassword);
    }

    @Test(expected = PersistenceException.class)
    public void updateWithExistingLogin() {
        final User user = userRepo.getOne(1L);
        userRepo.save(user.setLogin("user2"));
    }

    @Test
    public void deleteExistingUser() {
        final User newUser = new User().setLogin("deleteExistingUser").setPassword("pass");
        final long id = userRepo.save(newUser).getId();
        assertTrue(userRepo.findById(id).isPresent());
        final User user = userRepo.getOne(id);
        userRepo.delete(user);
        assertFalse(userRepo.findById(id).isPresent());
    }

    @Test(expected = PersistenceException.class)
    public void deleteNonexistentUser() {
        userRepo.delete(new User());
    }

    @Test
    public void deleteUserByExistingId() {
        final User newUser = new User().setLogin("deleteUserByExistingId").setPassword("pass");
        final long id = userRepo.save(newUser).getId();
        assertTrue(userRepo.findById(id).isPresent());
        userRepo.deleteById(id);
        assertFalse(userRepo.findById(id).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteByNonexistentId() {
        userRepo.deleteById(Long.MAX_VALUE);
    }

    @Test
    public void findByExistingId() {
        final Optional<User> optionalUser = userRepo.findById(1L);
        assertTrue(optionalUser.isPresent());
        assertEquals("user1", optionalUser.get().getLogin());
    }

    @Test
    public void findByNonexistentId() {
        assertFalse(userRepo.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    @Transactional(timeout = 10)
    public void getOneExisting() {
        final User user = userRepo.getOne(1L);
        assertEquals("user1", user.getLogin());
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional(timeout = 10)
    public void getOneNonexistent() {
        assertNull(userRepo.getOne(Long.MAX_VALUE));
    }

    @Test
    public void findByExistingLogin() {
        final String login = "user1";
        final Optional<User> optionalUser = userRepo.findByLogin(login);
        assertTrue(optionalUser.isPresent());
        assertEquals(login, optionalUser.get().getLogin());
        assertEquals("test1", optionalUser.get().getPassword());
    }

    @Test
    public void findByNonexistentLogin() {
        assertFalse(userRepo.findByLogin("user404").isPresent());
    }

    @Test
    @Transactional(timeout = 10)
    public void getByExistingLogin() {
        final String login = "user1";
        final User user = userRepo.getByLogin(login);
        assertNotNull(user);
        assertEquals(login, user.getLogin());
        assertEquals("test1", user.getPassword());
    }

    @Test
    @Transactional(timeout = 10)
    public void getByNonexistentLogin() {
        assertNull(userRepo.getByLogin("user404"));
    }

}
