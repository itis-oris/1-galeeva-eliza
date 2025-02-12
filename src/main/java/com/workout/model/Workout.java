package com.workout.model;

import java.sql.Date;

public record Workout(Long id, Long user_id, Date date) {
    @Override
    public String toString() {
        return "%s %s %s".formatted(id, user_id, date);
    }
}


