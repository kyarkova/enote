package com.enote.service;


import com.enote.entity.User;

import java.util.List;

public interface UserService {

    long countUsers();

    void create(String login, String password);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);
}
