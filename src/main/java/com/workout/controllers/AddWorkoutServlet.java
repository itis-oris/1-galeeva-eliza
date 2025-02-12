package com.workout.controllers;

import com.workout.model.Category;
import com.workout.model.Exercise;
import com.workout.model.Workout;
import com.workout.service.*;
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
import java.sql.Date;
import java.util.List;

@WebServlet("/add_workout")
public class AddWorkoutServlet extends HttpServlet {
    private ExerciseService exerciseService;
    private WorkoutService workoutService;
    private CategoryService categoryService;
    private List<Exercise> exercises;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        exerciseService = (ExerciseService) servletContext.getAttribute("exerciseService");
        workoutService = (WorkoutService) servletContext.getAttribute("workoutService");
        categoryService = (CategoryService) servletContext.getAttribute("categoryService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("add_workout_1.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String category = request.getParameter("category");
            String exercise = request.getParameter("exercise");
            if (!exerciseExists(exercise)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_workout_2.jsp");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>sorry this exercise doesn't exist in db :(</font>");
                rd.include(request, response);
            } else if (!categoryExists(category)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_workout_2.jsp");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>sorry this category doesn't exist in db :(</font>");
                rd.include(request, response);
            } else if (!category.isEmpty() && !exercise.isEmpty() && !request.getParameter("sets").isEmpty()) {
                int sets = Integer.parseInt(request.getParameter("sets"));
                HttpSession session = request.getSession(false);
                session.setAttribute("sets", sets);
                session.setAttribute("category", category);
                session.setAttribute("exercise", exercise);
                request.setAttribute("sets", sets);
                request.setAttribute("category", category);
                request.setAttribute("exercise", exercise);
                StringBuilder rows = new StringBuilder();
                for (int i = 0; i < sets; i++) {
                    String myRow = this.row;
                    while (myRow.contains("${set}")) {
                        myRow = myRow.replace("${set}", String.valueOf(i + 1));
                    }
                    rows.append(myRow);
                    rows.append("\n");
                }
                String myTable = this.table;
                myTable = myTable.replace("${exercise}", exercise);
                myTable = myTable.replace("${category}", category);
                myTable = myTable.replace("${rows}", rows);
                request.setAttribute("tables", myTable);
                if (session.getAttribute("workout_id") == null) {
                    addWorkout(session);
                    exercises = getAllExercises(categoryService.getIdByCategoryName(category).get());
                }
                session.removeAttribute("insertedSuccessfully");
                request.setAttribute("template", "add_workout_1.ftl");
                request.getRequestDispatcher("/templates/add_workout_1.ftl").forward(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_workout_2.jsp");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>the fields cannot be empty ;(</font>");
                rd.include(request, response);
            }
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean exerciseExists(String exercise) {
        return exerciseService.getIdByExerciseName(exercise).isPresent();
    }

    private boolean categoryExists(String category) {
        return categoryService.getIdByCategoryName(category).isPresent();
    }

    private List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    private List<Exercise> getAllExercises(Long category_id) {
        return exerciseService.getAllByCategoryId(category_id);
    }

    private void addWorkout(HttpSession session) {
        Long id = workoutService.getNextId().get();
        session.setAttribute("workout_id", id);
        System.out.println(session.getAttribute("username"));
        // Long user_id = userDao.getIdByUsername((String) session.getAttribute("username"));
        Long user_id = (Long) session.getAttribute("user_id");
        System.out.println(user_id);
        Date date = new Date(System.currentTimeMillis());
        Workout workout = new Workout(id, user_id, date);
        System.out.println(id + " " + user_id + " " + date);
        workoutService.deleteById(101450L);
        workoutService.save(workout);
    }

    private final String table = """
            <div class="container">
                    <h2>${exercise} (${category})</h2>
                    <ul class="responsive-table">
                        <li class="table-header">
                            <div class="col col-1">set</div>
                            <div class="col col-2">reps</div>
                            <div class="col col-3">weight</div>
                        </li>
                        ${rows}
                    </ul>
                </div>
            """;

    private final String row = """
            <li class="table-row">
                  <td>
                  <div class="col col-1" data-label="set">${set}</div>
                  </td>
                <div class="col col-2" data-label="reps${set}">
                  <td>
                    <div class="form-group">
                    <input type="text" name="reps${set}" id ="reps${set}"> </>
                    </div>
                  </td>
                </div>
                <div class="col col-3" data-label="weight${set}">
                  <td>
                  <div class="form-group">
                    <input type="text" name="weight${set}" id ="weight${set}"> </>
                  </div> 
                  </td>
                </div>
            </li>
            """;
}