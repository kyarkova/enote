package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Tag;
import com.enote.repo.TagRepo;
import com.enote.service.TagService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class TagServiceTest {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepo tagRepo;

    @Test
    @Ignore
    public void testCountTags() {
        int expectedCount = tagRepo.findAll().size();
        long countUsers = tagService.countTags();
        assertEquals(expectedCount, countUsers);
    }


    @Test
    public void testCreateTag() {
        tagService.create("testTagService");
        assertNotNull(tagRepo.getByName("testTagService"));
    }

    @Test(expected = PersistenceException.class)
    public void testCreateTagNonUnique() {
        final String nonUniqueName = "nonUniqueName";
        tagService.create(nonUniqueName);
        tagService.create(nonUniqueName);
    }

    @Test
    public void testFindAll() {
        int expected = tagRepo.findAll().size();
        int actual = tagService.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    @Ignore
    public void testDeleteExistingTagById() {
        final Tag testTag = new Tag().setName("testDeleteExistingTagById");
        final long testDeleteById = tagRepo.save(testTag).getId();
        Assert.assertTrue(tagRepo.findById(testDeleteById).isPresent());
        tagService.deleteById(testDeleteById);
        assertFalse(tagRepo.findById(testDeleteById).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteNonexistentNoteById() {
        final long nonexistentTagId = 99L;
        assertFalse(tagRepo.findById(nonexistentTagId).isPresent());
        tagService.deleteById(nonexistentTagId);
    }
}
