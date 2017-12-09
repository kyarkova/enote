package com.enote.repos;

import com.enote.entity.User;

import java.util.Set;

public interface UserRepo {

    User findById(Long id);

    int createUser(String login, String password);

    Set<User> findAll();

    int deleteById(long l);

    int updatePassword(long l, String newpass);

    Set<User> findAllByLogin(String login, boolean b);
}