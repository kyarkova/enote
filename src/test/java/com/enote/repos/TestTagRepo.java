package com.enote.repos;

import com.enote.config.AppConfig;
import com.enote.config.TestDataConfig;
import com.enote.entity.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
public class TestTagRepo {

    @Autowired
    TagRepo tagRepo;

    @Before
    public void setUp() {
        assertNotNull(tagRepo);
    }

    @Test
    public void testFindById() {
        Tag tag = tagRepo.findById(1L);
        assertEquals("tag1", tag.getName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        Tag tag = tagRepo.findById(99L);
        assertEquals("sometag", tag.getName());
    }
}
