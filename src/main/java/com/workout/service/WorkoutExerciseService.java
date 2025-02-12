package com.workout.service;

import com.workout.mapper.WorkoutExerciseRecordMapper;
import com.workout.model.WorkoutExercise;
import com.workout.repository.dao.WorkoutExerciseDao;

import java.util.List;
import java.util.Optional;

public class WorkoutExerciseService {
    private final WorkoutExerciseDao workoutExerciseDao = new WorkoutExerciseDao(new WorkoutExerciseRecordMapper());

    public List<WorkoutExercise> getAllByExerciseAndWorkoutId(Long exercise_id, Long workout_id) {
        return workoutExerciseDao.getAllByExerciseAndWorkoutId(exercise_id, workout_id);
    }

    public List<Long> getAllExerciseIdsByWorkoutId(Long workout_id) {
        return workoutExerciseDao.getAllExerciseIdsByWorkoutId(workout_id);
    }

    public List<WorkoutExercise> getAllByExerciseId(Long exercise_id) {
        return workoutExerciseDao.getAllByExerciseId(exercise_id);
    }

    public List<WorkoutExercise> getAllByWorkoutId(Long workout_id) {
        return workoutExerciseDao.getAllByWorkoutId(workout_id);
    }

    public List<WorkoutExercise> getAll() {
        return workoutExerciseDao.getAll();
    }

    public void save(WorkoutExercise entity) {
        workoutExerciseDao.save(entity);
    }


    public Optional<WorkoutExercise> findById(final Long id) {
        return workoutExerciseDao.findById(id);
    }

    public boolean deleteById(final Long id) {
        return workoutExerciseDao.deleteById(id);
    }
}
