package com.foodapp.servlet;

import com.foodapp.dao.CartDAO;
import com.foodapp.dao.impl.CartDAOImpl;
import com.foodapp.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart-count")
public class CartCountServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int count = 0;
        if (user != null) {
            count = cartDAO.getCartItemCount(user.getUserId());
        }

        response.getWriter().write("{\"count\":" + count + "}");
    }
}
