package com.workout.controllers;

import com.workout.model.Workout;
import com.workout.model.WorkoutExercise;
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
import java.util.List;


@WebServlet("/workouts")
public class WorkoutsServlet extends HttpServlet {
    private WorkoutService workoutService;
    private WorkoutExerciseService workoutExerciseService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        workoutService = (WorkoutService) servletContext.getAttribute("workoutService");
        workoutExerciseService = (WorkoutExerciseService) servletContext.getAttribute("workoutExerciseService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(false);
            Long workout_id = (Long) session.getAttribute("workout_id");
            List<WorkoutExercise> workoutExercise = getAllWorkoutExercises(workout_id);
            request.setAttribute("workout_exercise", workoutExercise);
            request.setAttribute("template", "view_workouts.ftl");
            request.getRequestDispatcher("/templates/view_workouts.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Workout> getAllWorkouts(Long user_id) {
        return workoutService.getAllByUserId(user_id);
    }

    public List<WorkoutExercise> getAllWorkoutExercises(Long workout_id) {
        return workoutExerciseService.getAllByWorkoutId(workout_id);
    }
}