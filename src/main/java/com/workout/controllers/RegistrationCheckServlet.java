package com.workout.controllers;

import com.workout.model.User;
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
import java.util.List;

@WebServlet("/registration_check")
public class RegistrationCheckServlet extends HttpServlet {
    private BCryptService bCryptService;
    private UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        bCryptService = (BCryptService) servletContext.getAttribute("bCryptService");
        userService = (UserService) servletContext.getAttribute("userService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        if (!checkUsernameEmail(username, email)) {
            saveUser(username, email, password);
            response.sendRedirect("index.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>choose another username this one is already taken ;(</font>");
            rd.include(request, response);
        }

    }

    private boolean checkUsernameEmail(String username, String email) {
        List<User> users = userService.getAll();
        for (User user : users) {
            if (user.username().equals(username) || user.email().equals(email)) {
                return true;
            }
        }
        return false;
    }


    private void saveUser(String username, String email, String password) {
        String salt = bCryptService.genSalt();
        User user = new User(0L, username, email, bCryptService.getHashedAndSaltedPassword(password, salt), salt);
        userService.save(user);
    }
}