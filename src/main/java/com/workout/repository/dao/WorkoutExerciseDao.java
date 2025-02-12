package com.workout.repository.dao;

import com.workout.mapper.WorkoutExerciseRecordMapper;
import com.workout.model.WorkoutExercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutExerciseDao extends AbstractDao<WorkoutExercise> {

    //language=sql
    private static final String SQL_SAVE = "INSERT INTO workout_exercise (workout_id, exercise_id, exercise_sets, exercise_reps, weight) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_GET_ALL = "SELECT * FROM workout_exercise";
    private static final String SQL_GET_ALL_BY_WORKOUT_ID = "SELECT * FROM workout_exercise WHERE workout_id = ?";
    private static final String SQL_GET_EXERCISE_IDS_BY_WORKOUT_ID = "SELECT DISTINCT(exercise_id) FROM workout_exercise WHERE workout_id = ?";
    private static final String SQL_GET_ALL_BY_EXERCISE_AND_WORKOUT_ID = "SELECT * FROM workout_exercise WHERE workout_id = ? AND exercise_id = ?";
    private static final String SQL_GET_ALL_BY_EXERCISE_ID = "SELECT * FROM workout_exercise WHERE exercise_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM workout_exercise WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM workout_exercise WHERE id = ?";

    public WorkoutExerciseDao(WorkoutExerciseRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<WorkoutExercise> getAllByExerciseAndWorkoutId(Long exercise_id, Long workout_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_BY_EXERCISE_AND_WORKOUT_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, workout_id);
            preparedStatement.setLong(2, exercise_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                WorkoutExercise workoutExercise = mapper.mapRow(executedQuery, 0);
                workoutExerciseList.add(workoutExercise);
            }
            return workoutExerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements by exercise and workout id sorry :(");
        }
    }

    public List<Long> getAllExerciseIdsByWorkoutId(Long workout_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_EXERCISE_IDS_BY_WORKOUT_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, workout_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Long> workoutExerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                Long exercise_id = executedQuery.getLong("exercise_id");
                workoutExerciseList.add(exercise_id);
            }
            return workoutExerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all  by  id sorry :(");
        }
    }

    public List<WorkoutExercise> getAllByExerciseId(Long exercise_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_BY_EXERCISE_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, exercise_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                WorkoutExercise workoutExercise = mapper.mapRow(executedQuery, 0);
                workoutExerciseList.add(workoutExercise);
            }
            return workoutExerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements by exercise id sorry :(");
        }
    }

    public List<WorkoutExercise> getAllByWorkoutId(Long workout_id) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL_BY_WORKOUT_ID, Optional.empty());
        try {
            preparedStatement.setLong(1, workout_id);
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                WorkoutExercise workoutExercise = mapper.mapRow(executedQuery, 0);
                workoutExerciseList.add(workoutExercise);
            }
            return workoutExerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements by workout id sorry :(");
        }
    }

    public List<WorkoutExercise> getAll() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
            while (executedQuery.next()) {
                workoutExerciseList.add(mapper.mapRow(executedQuery, 0));
            }
            return workoutExerciseList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements sorry :(");
        }
    }

    @Override
    public void save(WorkoutExercise entity) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_SAVE, Optional.empty());
        try {
            preparedStatement.setLong(1, entity.workout_id());
            preparedStatement.setLong(2, entity.exercise_id());
            preparedStatement.setInt(3, entity.sets());
            preparedStatement.setInt(4, entity.reps());
            preparedStatement.setInt(5, entity.weight());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("couldn't save entity to the table");
        }
    }


    public Optional<WorkoutExercise> findById(final Long id) {
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
