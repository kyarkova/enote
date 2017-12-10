package com.enote.repo;

import com.enote.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {

    Collection<Tag> getByName(String name);

    @Query("select t from Tag t inner join t.notes n where n.id = :id")
    Collection<Tag> getByNoteId(@Param("id") Long noteId);

    @Query("select count(id) from Tag t")
    long countTags();

    Tag getById(Long id);

}
