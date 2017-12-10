package com.enote.service.impl;

import com.enote.entity.Tag;
import com.enote.repo.TagRepo;
import com.enote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class TagServiceImpl implements TagService {

    private TagRepo tagRepo;

    @Autowired
    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public long countTags() {
        return tagRepo.countTags();
    }

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void create(String name) {
        Tag tag = new Tag()
                .setName(name);
        tagRepo.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagRepo.deleteById(id);
    }
}
