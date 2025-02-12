package com.workout.repository.dao;

import com.workout.mapper.ExerciseRecordMapper;
import com.workout.model.Exercise;
import com.workout.model.WorkoutExercise;
import com.workout.service.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExerciseDao extends CustomAbstractDao<Exercise> {
    //language=sql
    private static final String SQL_GET_ALL_BY_CATEGORY_ID = "SELECT * FROM exercise WHERE category_id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM exercise";
    private static final String SQL_GET_CATEGORY_BY_EXERCISE_ID = "SELECT c.name FROM exercise e, category c WHERE e.category_id = c.id AND e.id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM exercise WHERE id = ?";

    public ExerciseDao(ExerciseRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<Long> getAllExercises() {
        WorkoutExerciseDao workoutExerciseDao = Configuration.getWorkoutExerciseDao();
        List<WorkoutExercise> workoutExercises = workoutExerciseDao.getAll();
        Set<Long> exercises = new HashSet<>();
        for (WorkoutExercise workoutExercise : workoutExercises) {
            exercises.add(findById(workoutExercise.exercise_id()).get().id());
        }
        return exercises.stream().toList();
    }

    public Optional<Long> getIdByExerciseName(String exerciseName) {
        List<Exercise> exercises = getAll();
        for (Exercise exercise : exercises) {
            if (exercise.name().equals(exerciseName))
                return Optional.of(exercise.id());
        }
        return Optional.empty();
    }

    public Optional<Exercise> findById(final Long id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_FIND_BY_ID, Optional.of(id));
        try {
            preparedStatement.setLong(1, id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            return executedQuery.next() ? Optional.ofNullable(mapper.mapRow(executedQuery, 0)) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(id);
        }
    }

    public String getCategoryByExerciseId(Long exercise_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_CATEGORY_BY_EXERCISE_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, exercise_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            executedQuery.next();
            return executedQuery.getString("name");
        } catch (SQLException e) {
            throw new DaoException("couldn't category by exercise id, sorry :(");
        }
    }

    public List<Exercise> getAllByCategoryId(Long category_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_BY_CATEGORY_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, category_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Exercise> exerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                exerciseList.add(mapper.mapRow(executedQuery, 0));
            }
            return exerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements by category id sorry :(");
        }
    }

    public List<Exercise> getAll() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Exercise> exerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                exerciseList.add(mapper.mapRow(executedQuery, 0));
            }
            return exerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements sorry :(");
        }
    }
}
