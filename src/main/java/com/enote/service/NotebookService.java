package com.enote.service;

import com.enote.entity.Notebook;
import com.enote.entity.User;

import java.util.List;

public interface NotebookService {

    long countNotebooks();

    void create(String name, User user);

    List<Notebook> findAll();

    List<Notebook> findAllByUser(User user);

    void deleteById(Long id);
}
