package com.workout.controllers;

import com.workout.model.WorkoutExercise;
import com.workout.service.ExerciseService;
import com.workout.service.WorkoutExerciseService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exercise_check")
public class ExerciseCheckServlet extends HttpServlet {

    private ExerciseService exerciseService;
    private WorkoutExerciseService workoutExerciseService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        exerciseService = (ExerciseService) servletContext.getAttribute("exerciseService");
        workoutExerciseService = (WorkoutExerciseService) servletContext.getAttribute("workoutExerciseService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int sets = (int) session.getAttribute("sets");
        String exercise = (String) session.getAttribute("exercise");
        if (session.getAttribute("insertedSuccessfully") == null) {
            for (int i = 1; i <= sets; i++) {
                if (isInt(request.getParameter("reps" + i)) && isInt(request.getParameter("weight" + i))) {
                    int reps = Integer.parseInt(request.getParameter("reps" + i));
                    int weight = Integer.parseInt(request.getParameter("weight" + i));
                    saveWorkoutExercise(session, getExerciseId(exercise), i, reps, weight);
                } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/templates/add_workout_1.ftl");
                    PrintWriter out = response.getWriter();
                    out.println("<font color=red>the fields cannot be empty;(</font>");
                    rd.include(request, response);
                }
            }
            session.setAttribute("insertedSuccessfully", "true");
        }
        getServletContext().getRequestDispatcher("/add_workout_1.jsp").forward(request, response);
    }

    private void saveWorkoutExercise(HttpSession session, Long exercise_id, int set, int reps, int weight) {
        Long workout_id = (Long) session.getAttribute("workout_id");
        WorkoutExercise workoutExercise = new WorkoutExercise(workout_id, exercise_id, set, reps, weight);
        workoutExerciseService.save(workoutExercise);
    }

    private Long getExerciseId(String exercise) {
        return exerciseService.getIdByExerciseName(exercise).get();
    }

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

}

