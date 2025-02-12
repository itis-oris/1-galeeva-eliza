package com.workout.mapper;

import com.workout.model.Exercise;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseRecordMapper implements RowMapper<Exercise> {
    @Override
    public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Exercise(rs.getLong("id"), rs.getLong("category_id"), rs.getString("name"));
    }
}
