package com.enote.controller;

import com.enote.config.AppConfig;
import com.enote.config.PersistenceConfig;
import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.service.NoteService;
import org.h2.server.web.WebApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, AppConfig.class, WebApp.class})
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteController noteController;

    @Mock
    private NoteService mockNoteService;

    @Before
    public void setUp() {
        assertNotNull(noteController);
        assertNotNull(mockNoteService);
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetList() throws Exception {
        List<Note> expectedNotes = asList(new Note());
        when(mockNoteService.findAllByNotebook(new Notebook())).thenReturn(expectedNotes);

        mockMvc.perform(get("/notebook/99/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notebook/notes"))
                .andExpect(model().attribute("notes", hasSize(1)));
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(
                post("/notebook/99/note"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/note/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/note"))
                .andExpect(model().attribute("note", hasProperty("title", is("note1"))));
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put("/note/{noteId}", 99))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Note note = new Note();
        noteController.add(1L, note);
        Long noteId = note.getId();
        mockMvc.perform(delete("/note/{noteId}", noteId))
                .andExpect(status().isOk());
    }
}
