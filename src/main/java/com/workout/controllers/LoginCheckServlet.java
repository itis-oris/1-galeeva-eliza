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
import java.util.Map;
import java.util.UUID;

@WebServlet("/login_check")
public class LoginCheckServlet extends HttpServlet {
    private BCryptService bCryptService;
    private UserService userService;
    Map<UUID, Long> userSessions;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        bCryptService = (BCryptService) servletContext.getAttribute("bCryptService");
        userService = (UserService) servletContext.getAttribute("userService");
        userSessions = (Map<UUID, Long>) servletContext.getAttribute("USER_SESSIONS");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = getUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            if (session.getAttribute("uuid") == null || !checkUUID((String) session.getAttribute("uuid"))) {
                UUID uuid = UUID.randomUUID();
                session.setAttribute("uuid", uuid.toString());
                session.setAttribute("username", username);
                session.setAttribute("user_id", user.id());
                session.setMaxInactiveInterval(3 * 60);
                writeToSession(session, user);
            }
            response.sendRedirect("index.html");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>either user name or password is wrong.</font>");
            rd.include(request, response);
        }
    }

    private User getUser(String username, String password) {
        List<User> users = userService.getAll();
        for (User user : users) {
            if (user.username().equals(username) && bCryptService.checkPassword(user.password(), password, user.salt())) {
                return user;
            }
        }
        return null;
    }

    private boolean checkUUID(String uuid) {
        return userSessions.containsKey(UUID.fromString(uuid));
    }

    private void writeToSession(HttpSession session, User user) {
        userSessions.put(UUID.fromString((String) session.getAttribute("uuid")), user.id());
        getServletContext().setAttribute("USER_SESSIONS", userSessions);
    }
}
