package com.workout.service;

import com.workout.mapper.ExerciseRecordMapper;
import com.workout.model.Exercise;
import com.workout.repository.dao.ExerciseDao;

import java.util.*;

public class ExerciseService {
    private final ExerciseDao exerciseDao = new ExerciseDao(new ExerciseRecordMapper());
    public List<Long> getAllExercises() {
        return exerciseDao.getAllExercises();
    }

    public Optional<Long> getIdByExerciseName(String exerciseName) {
        return exerciseDao.getIdByExerciseName(exerciseName);
    }

    public Optional<Exercise> findById(final Long id) {
        return exerciseDao.findById(id);
    }

    public String getCategoryByExerciseId(Long exercise_id) {
       return exerciseDao.getCategoryByExerciseId(exercise_id);
    }

    public List<Exercise> getAllByCategoryId(Long category_id) {
        return exerciseDao.getAllByCategoryId(category_id);
    }

    public List<Exercise> getAll() {
       return exerciseDao.getAll();
    }
}
