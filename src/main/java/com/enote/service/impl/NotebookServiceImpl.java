package com.enote.service.impl;

import com.enote.entity.Notebook;
import com.enote.entity.User;
import com.enote.repo.NotebookRepo;
import com.enote.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class NotebookServiceImpl implements NotebookService {

    private NotebookRepo notebookRepo;

    @Autowired
    public NotebookServiceImpl(NotebookRepo notebookRepo) {
        this.notebookRepo = notebookRepo;
    }

    @Override
    public long countNotebooks() {
        return notebookRepo.countNotebooks();
    }

    @Override
    @Transactional(readOnly = false)
    public void create(String name, User user) {
        Notebook notebook = new Notebook()
                .setName(name)
                .setUser(user);
        notebookRepo.save(notebook);
    }

    @Override
    public List<Notebook> findAll() {
        return notebookRepo.findAll();
    }

    @Override
    public List<Notebook> findAllByUser(User user) {
        return notebookRepo.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        notebookRepo.deleteById(id);
    }
}
