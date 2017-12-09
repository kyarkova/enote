package com.enote.repos;

import com.enote.entity.Notebook;

import java.util.Set;

public interface NotebookRepo {

    Notebook findById(Long id);

    int createNotebook(String name, Long userId);

    Set<Notebook> findByUserId(Long user);
}
