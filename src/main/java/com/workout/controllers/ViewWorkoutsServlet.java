package com.workout.controllers;

import com.workout.model.ViewWorkout;
import com.workout.model.Workout;
import com.workout.model.WorkoutExercise;
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

@WebServlet("/view_workouts")
public class ViewWorkoutsServlet extends HttpServlet {
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
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(false);
            Long user_id = (Long) session.getAttribute("user_id");
            int rows = workoutService.countRows();
            int currentPage;
            if (request.getParameter("page") != null) {
                System.out.println(request.getParameter("page"));
                currentPage = Integer.parseInt(request.getParameter("page"));
            } else {
                currentPage = 1;
            }
            List<ViewWorkout> viewWorkout;
            Workout workout;
            if (request.getAttribute("viewWorkout") != null) {
                viewWorkout = (List<ViewWorkout>) request.getAttribute("viewWorkout");
                workout = (Workout) request.getAttribute("workout");
            } else if (currentPage == 0) {
                currentPage++;
                viewWorkout = getWorkout(getAllWorkoutExercises(getWorkoutForPage(user_id, 0)));
                workout = workoutService.findById(getWorkoutForPage(user_id,0)).get();
            } else if (rows + 1 == currentPage) {
                currentPage--;
                viewWorkout = getWorkout(getAllWorkoutExercises(getWorkoutForPage(user_id, currentPage - 1)));
                workout = workoutService.findById(getWorkoutForPage(user_id,currentPage - 1)).get();
            } else {
                viewWorkout = getWorkout(getAllWorkoutExercises(getWorkoutForPage(user_id, currentPage - 1)));
                workout = workoutService.findById(getWorkoutForPage(user_id,currentPage - 1)).get();
            }
            request.setAttribute("page", currentPage);
            System.out.println(currentPage);
            request.setAttribute("viewWorkout", viewWorkout);
            request.setAttribute("workout", workout);
            request.setAttribute("template", "view_workouts.ftl");
            request.getRequestDispatcher("/templates/view_workouts.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getWorkoutForPage(Long user_id, int page) {
        return workoutService.getWorkoutByPage(user_id, page).get().id();
    }

    public List<Workout> getAllWorkouts(Long user_id) {
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