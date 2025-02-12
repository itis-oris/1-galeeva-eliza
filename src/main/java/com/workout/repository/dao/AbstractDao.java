package com.workout.repository.dao;

import com.workout.service.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    RowMapper<T> mapper;

    abstract Optional<T> findById(Long id);

    abstract List<T> getAll();

    abstract boolean deleteById(final Long id);

    abstract void save(T entity);

    public Optional<Connection> getConnection(){
        return Configuration.getConnection();
    }
    protected PreparedStatement getPreparedStatement(String SQL_QUERY, Optional<Long> id) {
        if (id.isPresent()) {
            return getConnection()
                    .map(connection -> {
                        try {
                            return connection.prepareStatement(SQL_QUERY);
                        } catch (SQLException e) {
                            throw new DaoException(id.get());
                        }
                    }).orElseThrow(DaoException::new);
        }
        return getConnection()
                .map(connection -> {
                    try {
                        return connection.prepareStatement(SQL_QUERY);
                    } catch (SQLException e) {
                        throw new DaoException();
                    }
                }).orElseThrow(DaoException::new);
    }
}