package com.enote.repos;

import com.enote.config.AppConfig;
import com.enote.config.TestDataConfig;
import com.enote.entity.Notebook;
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
public class TestNotebookRepo {

    @Autowired
    NotebookRepo notebookRepo;

    @Before
    public void setUp() {
        assertNotNull(notebookRepo);
    }

    @Test
    public void testFindById() {
        Notebook notebook = notebookRepo.findById(1L);
        assertEquals("notebook1-1", notebook.getName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        Notebook notebook = notebookRepo.findById(99L);
        assertEquals("somenotebook", notebook.getName());
    }
}
