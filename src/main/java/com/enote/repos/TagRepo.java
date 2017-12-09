package com.enote.repos;

import com.enote.entity.Tag;

import java.util.Set;

public interface TagRepo {

    Tag findById(Long id);

    int createTag(String name);

    Set<Tag> findByNoteId(Long noteId);
}
