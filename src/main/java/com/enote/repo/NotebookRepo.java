package com.enote.repo;

import com.enote.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NotebookRepo extends JpaRepository<Notebook, Long> {

    Collection<Notebook> getByUserId(Long userId);

    Collection<Notebook> getByName(String name);

}
