package com.enote.service;

import com.enote.entity.Tag;

import java.util.List;

public interface TagService {

    Long countTags();

    void create(String name);

    List<Tag> findAll();

    void deleteById(Long id);
}
