package com.workout.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptService {
    private final static BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    public String genSalt() {
        return BCrypt.gensalt(10);
    }

    public String getHashedAndSaltedPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public boolean checkPassword(String hashedAndSaltedPassword, String password, String salt) {
        return BCrypt.checkpw(password, hashedAndSaltedPassword);
    }
}
