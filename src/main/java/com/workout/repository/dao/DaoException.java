package com.workout.repository.dao;

public class DaoException extends RuntimeException {
    public DaoException(final Long id) {
        super("can't exectute query for id = %s".formatted(id));
    }
    public DaoException() {
        super("can't connect");
    }
    public DaoException(String errorMessage) {
        super(errorMessage);
    }
}

