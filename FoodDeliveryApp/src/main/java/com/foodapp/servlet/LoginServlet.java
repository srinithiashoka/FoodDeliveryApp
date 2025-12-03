package com.foodapp.servlet;

import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Dummy login logic (replace with DB check)
        if ("admin".equals(username) && "123".equals(password)) {
            User user = new User();
            user.setUserId(1);
            user.setUsername(username);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.getWriter().write("login success");
        } else {
            response.getWriter().write("login failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
