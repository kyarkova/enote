package com.enote.repo;

import com.enote.entity.Notebook;
import com.enote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface NotebookRepo extends JpaRepository<Notebook, Long> {

    Collection<Notebook> getByUserId(Long userId);

    Collection<Notebook> getByName(String name);

    Long countNotebooks();

    List<Notebook> findAllByUser(User user);
}
