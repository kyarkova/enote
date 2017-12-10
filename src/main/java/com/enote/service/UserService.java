package com.enote.service;


import com.enote.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Long countUsers();

    void create(String login, String password);

    User getByLogin(String login);

    List<User> findAll();

    Optional<User> findById(Long id);

    void deleteById(Long id);
}
