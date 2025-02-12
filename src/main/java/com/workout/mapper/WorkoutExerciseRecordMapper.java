package com.workout.mapper;

import com.workout.model.WorkoutExercise;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutExerciseRecordMapper implements RowMapper<WorkoutExercise> {
    @Override
    public WorkoutExercise mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WorkoutExercise(rs.getLong("workout_id"), rs.getLong("exercise_id"), rs.getInt("exercise_sets"), rs.getInt("exercise_reps"), rs.getInt("weight"));
    }
}
