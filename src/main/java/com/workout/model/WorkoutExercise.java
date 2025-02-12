package com.workout.model;

public record WorkoutExercise(Long workout_id, Long exercise_id, int sets, int reps, int weight) {
    @Override
    public String toString() {
        return "%s %s %s %s %s".formatted(workout_id, exercise_id, sets, reps, weight);
    }
}
