package com.workout.model;

public record ViewWorkout(String category, String exercise, int sets, int reps, int weight) {
    @Override
    public String toString() {
        return "%s %s %s %s %s".formatted(category, exercise, sets, reps, weight);
    }
}
