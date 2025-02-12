package com.workout.service;

import com.workout.mapper.WorkoutRecordMapper;
import com.workout.model.Workout;
import com.workout.repository.dao.WorkoutDao;

import java.util.List;
import java.util.Optional;

public class WorkoutService {
    private final WorkoutDao workoutDao = new WorkoutDao(new WorkoutRecordMapper());

    public int countRows() {
        return workoutDao.countRows();
    }

    public Optional<Workout> getWorkoutByPage(Long user_id, int offset) {
        return workoutDao.getWorkoutByPage(user_id, offset);
    }

    public List<Workout> getAllByUserId(Long user_id) {
        return workoutDao.getAllByUserId(user_id);
    }

    public Optional<Long> getNextId() {
        return workoutDao.getNextId();
    }

    public List<Workout> getAll() {
        return workoutDao.getAll();
    }

    public void save(Workout entity) {
        workoutDao.save(entity);
    }


    public Optional<Workout> findById(final Long id) {
        return workoutDao.findById(id);
    }

    public boolean deleteById(final Long id) {
        return workoutDao.deleteById(id);
    }
}
