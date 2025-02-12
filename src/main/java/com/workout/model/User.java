package com.workout.model;

public record User(Long id, String username, String email, String password, String salt) {
}
