package com.workout.controllers;

import com.workout.model.*;
import com.workout.service.ExerciseService;
import com.workout.service.WorkoutExerciseService;
import com.workout.service.WorkoutService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;


@WebServlet("/progress")
public class ProgressServlet extends HttpServlet {
    private ExerciseService exerciseService;
    private WorkoutService workoutService;
    private WorkoutExerciseService workoutExerciseService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        exerciseService = (ExerciseService) servletContext.getAttribute("exerciseService");
        workoutService = (WorkoutService) servletContext.getAttribute("workoutService");
        workoutExerciseService = (WorkoutExerciseService) servletContext.getAttribute("workoutExerciseService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            Long user_id = (Long) session.getAttribute("user_id");
            Map<ExerciseCategory, List<Progress>> progress = getProgress(user_id);
            request.setAttribute("progress", progress);
            request.setAttribute("template", "progress.ftl");
            request.getRequestDispatcher("/templates/progress.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<ExerciseCategory, List<Progress>> getProgress(Long user_id) {
        Map<ExerciseCategory, List<Progress>> progressMap = new HashMap<>();
        for (Workout workout : getALlWorkouts(user_id)) {
            List<Long> exerciseIds = workoutExerciseService.getAllExerciseIdsByWorkoutId(workout.id());
            exerciseIds.forEach(System.out::println);
            for (Long exerciseId : exerciseIds) {
                List<WorkoutExercise> workoutExercises = workoutExerciseService.getAllByExerciseAndWorkoutId(exerciseId, workout.id());
                int count = workoutExercises.size();
                int reps = workoutExercises.stream().mapToInt(WorkoutExercise::reps).sum();
                int weight = workoutExercises.stream().mapToInt(WorkoutExercise::weight).sum();
                ExerciseCategory exerciseCategory = new ExerciseCategory(exerciseService.findById(exerciseId).get().name(), exerciseService.getCategoryByExerciseId(exerciseId));
                Progress progress = new Progress(workout.date(), reps / count, weight / count);
                if (!progressMap.containsKey(exerciseCategory))
                    progressMap.put(exerciseCategory, new ArrayList<>());
                progressMap.get(exerciseCategory).add(progress);
            }
        }
        return progressMap;
    }

    public List<Workout> getALlWorkouts(Long user_id) {
        return workoutService.getAllByUserId(user_id);
    }

    public List<WorkoutExercise> getAllWorkoutExercises(Long workout_id) {
        return workoutExerciseService.getAllByWorkoutId(workout_id);
    }

    public List<ViewWorkout> getWorkout(List<WorkoutExercise> workoutExercises) {
        List<ViewWorkout> workout = new ArrayList<>();
        for (WorkoutExercise we : workoutExercises) {
            String category = exerciseService.getCategoryByExerciseId(we.exercise_id());
            String exercise = exerciseService.findById(we.exercise_id()).get().name();
            ViewWorkout viewWorkout = new ViewWorkout(category, exercise, we.sets(), we.reps(), we.weight());
            workout.add(viewWorkout);

        }
        return workout;
    }
}