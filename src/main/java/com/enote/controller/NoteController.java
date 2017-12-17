package com.enote.controller;

import com.enote.entity.Note;
import com.enote.entity.Notebook;
import com.enote.service.NoteService;
import com.enote.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private NotebookService notebookService;

    @GetMapping("/notebook/{notebookId}/notes")
    @ResponseStatus(HttpStatus.OK)
    public String getList(@PathVariable Long notebookId, Model model) throws Exception {
        Notebook notebook = notebookService.findById(notebookId).orElseThrow(Exception::new);
        model.addAttribute("notes", noteService.findAllByNotebook(notebook));
        return "notebook/notes";
    }

    @PostMapping("/notebook/{notebookId}/note")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable Long notebookId, @RequestBody Note note) throws Exception {
        Notebook notebook = notebookService.findById(notebookId).orElseThrow(Exception::new);
        noteService.create(note.setNotebook(notebook));
    }

    @GetMapping("/note/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public String get(@PathVariable Long noteId, Model model) {
        model.addAttribute("note", noteService.findById(noteId));
        return "/note";
    }

    @PutMapping("/note/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Note note) {
        noteService.update(note);
    }

    @DeleteMapping("/note/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long noteId) {
        noteService.deleteById(noteId);
    }
}
