package com.workout.listeners;

import com.workout.service.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.*;


@WebListener
public class WorkoutContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        Map<UUID, Long> userSessions = new HashMap<>();
        sce.getServletContext().setAttribute("USER_SESSIONS", userSessions);
        CategoryService categoryService = new CategoryService();
        ExerciseService exerciseService = new ExerciseService();
        UserService userService = new UserService();
        WorkoutExerciseService workoutExerciseService = new WorkoutExerciseService();
        WorkoutService workoutService = new WorkoutService();
        BCryptService bCryptService = new BCryptService();
        sce.getServletContext().setAttribute("categoryService", categoryService);
        sce.getServletContext().setAttribute("exerciseService", exerciseService);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("workoutExerciseService", workoutExerciseService);
        sce.getServletContext().setAttribute("workoutService", workoutService);
        sce.getServletContext().setAttribute("bCryptService", bCryptService);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Map userSessions = (Map) sce.getServletContext().getAttribute("USER_SESSIONS");
        userSessions.clear();
    }
}
