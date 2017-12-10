package com.enote.services;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Tag;
import com.enote.repo.TagRepo;
import com.enote.service.TagService;
import org.junit.Ignore;
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
public class TagServiceTest {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepo tagRepo;

    @Test
    @Ignore
    public void testCountTags() {
        // TODO: count tags in test data and don't forget about created during test session
//        long countUsers = tagService.countTags();
//        assertEquals(4, countUsers);
    }


    @Test
    public void testCreateTag() {
        tagService.create("testTagService");
        assertNotNull(tagRepo.getByName("testTagService"));
    }

    @Test
    public void testFindAll() {
        List<Tag> all = tagService.findAll();
        assertNotNull(all);
    }

    @Test
    @Ignore
    public void testDeleteById() {
//        tagService.deleteById(4L);
//        assertNull(tagRepo.getOne(4L));
    }
}
