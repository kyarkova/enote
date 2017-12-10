package com.enote.service;

import com.enote.entity.Notebook;
import com.enote.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NotebookService {

    Long countNotebooks();

    void create(String name, User user);

    Collection<Notebook> getByName(String name);

    List<Notebook> findAll();

    List<Notebook> findAllByUser(User user);

    Optional<Notebook> findById(Long id);

    void deleteById(Long id);
}
