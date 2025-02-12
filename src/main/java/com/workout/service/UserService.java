package com.workout.service;

import com.workout.mapper.UserRecordMapper;
import com.workout.model.User;
import com.workout.repository.dao.UserDao;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDao userDao = new UserDao(new UserRecordMapper());

    public Long getIdByUsername(String username) {
        return userDao.getIdByUsername(username);
    }

    public Optional<Long> getNextId() {
        return userDao.getNextId();
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void save(User entity) {
        userDao.save(entity);
    }


    public Optional<User> findById(final Long id) {
        return userDao.findById(id);
    }

    public boolean deleteById(final Long id) {
        return userDao.deleteById(id);
    }
}
