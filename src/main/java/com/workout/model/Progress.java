package com.workout.model;

import java.sql.Date;

public record Progress(Date date, int reps, int weight) {
}
