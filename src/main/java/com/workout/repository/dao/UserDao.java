package com.workout.repository.dao;

import com.workout.mapper.UserRecordMapper;
import com.workout.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    //language=sql
    private static final String SQL_SAVE_IF_NOT_EXISTS = """
            INSERT INTO users (username, email, password, salt)
            SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT id FROM users WHERE username = ?)
            """;
    private static final String SQL_SAVE = "INSERT INTO users (id, username, email, password, salt) VALUES ((SELECT NEXTVAL('users_sequence')), ?, ?, ?, ?)";
    private static final String SQL_GET_ALL = "SELECT * FROM users";
    private static final String SQL_GET_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String SQL_GET_NEXT_ID = "SELECT nextval('users_sequence')";

    public UserDao(UserRecordMapper mapper) {
        this.mapper = mapper;
    }

    public Long getIdByUsername(String username) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_BY_USERNAME, Optional.empty());
        try {
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet executedQuery = preparedStatement.executeQuery();
            User user = mapper.mapRow(executedQuery, 0);
            return user.id();
        } catch (SQLException e) {
            throw new DaoException("there's no such username");
        }
    }

    public Optional<Long> getNextId() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_NEXT_ID, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            if (executedQuery.next()) {
                return Optional.ofNullable(executedQuery.getLong("nextval"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("couldn't get id sorry :(");
        }
    }

    public List<User> getAll() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (executedQuery.next()) {
                userList.add(mapper.mapRow(executedQuery, 0));
            }
            return userList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements sorry :(");
        }
    }

    @Override
    public void save(User entity) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_SAVE, Optional.empty());
        try {
            preparedStatement.setString(1, entity.username());
            preparedStatement.setString(2, entity.email());
            preparedStatement.setString(3, entity.password());
            preparedStatement.setString(4, entity.salt());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("couldn't save entity to the table");
        }
    }


    public Optional<User> findById(final Long id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_FIND_BY_ID, Optional.of(id));
        try {
            preparedStatement.setLong(1, id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            return executedQuery.next() ? Optional.ofNullable(mapper.mapRow(executedQuery, 0)) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(id);
        }
    }

    public boolean deleteById(final Long id) {
        if (findById(id).isPresent()) {
            PreparedStatement preparedStatement = getPreparedStatement(SQL_DELETE_BY_ID, Optional.of(id));
            try {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException();
            }
            return true;
        }
        return false;
    }
}