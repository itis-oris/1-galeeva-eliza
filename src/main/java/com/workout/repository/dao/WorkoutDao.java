package com.workout.repository.dao;

import com.workout.mapper.WorkoutRecordMapper;
import com.workout.model.Workout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutDao extends AbstractDao<Workout> {

    //language=sql
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM workout";
    private static final String SQL_SAVE = "INSERT INTO workout (id, user_id, date) VALUES (?,?,?)";
    private static final String SQL_GET_ALL_WITH_LIMIT = "SELECT * FROM workout WHERE user_id = ? ORDER BY id LIMIT 1 OFFSET ?";
    private static final String SQL_GET_ALL = "SELECT * FROM workout";
    private static final String SQL_GET_ALL_BY_USER_ID = "SELECT * FROM workout WHERE user_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM workout WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM workout WHERE id = ?";
    private static final String SQL_GET_NEXT_ID = "SELECT nextval('workout_sequence')";

    public WorkoutDao(WorkoutRecordMapper mapper) {
        this.mapper = mapper;
    }

    public int countRows() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_COUNT, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            executedQuery.next();
            return executedQuery.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Workout> getWorkoutByPage(Long user_id, int offset) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_WITH_LIMIT, Optional.empty());
        try {
            preparedStatement.setLong(1, user_id);
            preparedStatement.setInt(2, offset);
            ResultSet executedQuery = preparedStatement.executeQuery();
            return executedQuery.next() ? Optional.ofNullable(mapper.mapRow(executedQuery, 0)) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public List<Workout> getAllByUserId(Long user_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_BY_USER_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, user_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Workout> workoutList = new ArrayList<>();
            while (executedQuery.next()) {
                workoutList.add(mapper.mapRow(executedQuery, 0));
            }
            return workoutList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements by user's id sorry :(");
        }
    }

    public Optional<Long> getNextId() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_NEXT_ID, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            if (executedQuery.next()) {
                return Optional.of(executedQuery.getLong("nextval"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("couldn't get id sorry :(");
        }
    }

    public List<Workout> getAll() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Workout> workoutList = new ArrayList<>();
            while (executedQuery.next()) {
                workoutList.add(mapper.mapRow(executedQuery, 0));
            }
            return workoutList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements sorry :(");
        }
    }

    @Override
    public void save(Workout entity) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_SAVE, Optional.empty());
        try {
            preparedStatement.setLong(1, entity.id());
            preparedStatement.setLong(2, entity.user_id());
            preparedStatement.setDate(3, entity.date());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("couldn't save entity to the table");
        }
    }


    public Optional<Workout> findById(final Long id) {
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