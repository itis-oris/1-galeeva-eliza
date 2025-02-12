package com.workout.service;
import com.workout.repository.dao.*;
import com.workout.mapper.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class Configuration {
    public static UserRecordMapper getUserRecordMapper() {
        return new UserRecordMapper();
    }

    public static WorkoutRecordMapper getWorkoutRecordMapper() {
        return new WorkoutRecordMapper();
    }

    public static ExerciseRecordMapper getExerciseRecordMapper() {
        return new ExerciseRecordMapper();
    }

    public static WorkoutExerciseRecordMapper getWorkoutExerciseRecordMapper() {
        return new WorkoutExerciseRecordMapper();
    }

    public static CategoryRecordMapper getCategoryRecordMapper() {
        return new CategoryRecordMapper();
    }

    public static UserDao getUserDao() {
        return new UserDao(getUserRecordMapper());
    }

    public static WorkoutDao getWorkoutDao() {
        return new WorkoutDao(getWorkoutRecordMapper());
    }

    public static ExerciseDao getExerciseDao() {
        return new ExerciseDao(getExerciseRecordMapper());
    }

    public static WorkoutExerciseDao getWorkoutExerciseDao() {
        return new WorkoutExerciseDao(getWorkoutExerciseRecordMapper());
    }

    public static CategoryDao getCategoryDao() {
        return new CategoryDao(getCategoryRecordMapper());
    }

    public static Optional<Connection> getConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("D:\\IdeaProjects\\workout\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            Class.forName("org.postgresql.Driver");
            return Optional.ofNullable(DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")));
        } catch (SQLException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

}
