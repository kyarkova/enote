package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Tag;
import com.enote.repo.TagRepo;
import com.enote.service.TagService;
import org.junit.Assert;
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
    public void testCountTags() {
        int expectedCount = tagRepo.findAll().size();
        long countUsers = tagService.countTags();
        assertEquals(expectedCount, countUsers);
    }


    @Test
    public void testCreateTag() {
        final String tagName = "testCreateTag";
        tagService.create(tagName);
        assertNotNull(tagRepo.getByName(tagName));
    }

    @Test(expected = PersistenceException.class)
    public void testCreateTagNonUnique() {
        final String nonUniqueName = "testCreateTagNonUnique";
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
    public void testDeleteExistingTagById() {
        final String tagName = "testDeleteExistingTagById";
        final Tag testTag = new Tag().setName(tagName);
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
