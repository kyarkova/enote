package com.enote.service.impl;

import com.enote.entity.User;
import com.enote.repo.UserRepo;
import com.enote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public long countUsers() {
        return userRepo.countUsers();
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void create(String login, String password) {
        User user = new User()
                .setPassword(password)
                .setLogin(login);
        userRepo.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findOneById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

}

