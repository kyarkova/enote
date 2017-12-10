package com.enote.repo;

import com.enote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    User getByLogin(String login);

    @Query("select u from User u where u.id= :id")
    User findOneById(Long id);

    long countUsers();
}