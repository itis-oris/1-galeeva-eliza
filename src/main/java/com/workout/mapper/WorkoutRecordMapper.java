package com.workout.mapper;

import com.workout.model.Workout;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutRecordMapper implements RowMapper<Workout> {
    @Override
    public Workout mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Workout(rs.getLong("id"), rs.getLong("user_id"), rs.getDate("date"));
    }
}
